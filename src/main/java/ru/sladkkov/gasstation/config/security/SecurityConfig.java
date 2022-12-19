package ru.sladkkov.gasstation.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sladkkov.gasstation.security.UserDetailsServiceImpl;
import ru.sladkkov.gasstation.security.jwt.ConfigurerFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;


    private final ConfigurerFilter configurerFilter;


    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, ConfigurerFilter configurerFilter) {
        this.userDetailsService = userDetailsService;
        this.configurerFilter = configurerFilter;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("api/v1/login").permitAll()
                .antMatchers("api/v1/register").permitAll()
                .antMatchers("api/v1/admin/**").hasRole("ADMIN")
                .antMatchers("api/v1/user/**").hasRole("USER")
                .antMatchers("api/v1/topologyCreate").permitAll()
                .and()
                .apply(configurerFilter);

        return http.build();
    }

}
