package ru.sladkkov.gasstation.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.sladkkov.gasstation.exception.JwtAuthenticationException;
import ru.sladkkov.gasstation.model.Role;
import ru.sladkkov.gasstation.security.UserDetailsServiceImpl;


import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Value("${jwt.token.expired}")
    private String jwtExpirationMs;

    @Autowired
    public TokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    private Claims getClaims(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));
        return claims;
    }

    private List<String> getRoleNames(List<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().getAuthority())
                .toList();
    }

    public String createJwtToken(String username, List<Role> roles) {
        return Jwts.builder()
                .setClaims(getClaims(username, roles))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(Long.parseLong(jwtExpirationMs))))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (JwtAuthenticationException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Jwt token invalid or expired");
        }
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

}
