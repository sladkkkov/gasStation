package ru.sladkkov.gasstation.config.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.sladkkov.gasstation.dto.request.LoginRequest;
import ru.sladkkov.gasstation.dto.response.JwtResponse;
import ru.sladkkov.gasstation.model.User;
import ru.sladkkov.gasstation.security.jwt.TokenProvider;


@Service
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    public JwtResponse login(LoginRequest loginRequest) {
        log.info("Start authenticate user with: " + loginRequest.getUsername());
        String username = loginRequest.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword()));
        User user = userService.findByUsername(username);
        String token = tokenProvider.createJwtToken(username, user.getRoles());

        return JwtResponse.builder()
                .id(user.getId())
                .token(token)
                .username(username)
                .roles(user.getRoles())
                .build();
    }
}
