package com.techtwist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/product/secure").authenticated()
                .anyRequest().permitAll()
            )
            .oauth2Login() // Enable OAuth2 login
            .and()
            .logout().logoutSuccessUrl("/"); // Redirect after logout
        return http.build();
    }
}