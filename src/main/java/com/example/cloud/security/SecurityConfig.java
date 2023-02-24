package com.example.cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity

public class SecurityConfig implements WebMvcConfigurer {
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private final String ORIGIN_URL = "http://localhost:8080";

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {

        http
                .cors(httpSecurityCorsConfigurer -> {
                    CorsRegistry registry = new CorsRegistry();
                    registry.addMapping(LOGIN_URL)
                            .allowedOrigins(ORIGIN_URL)
                            .allowedMethods(String.valueOf(HttpMethod.POST));
                })
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, LOGIN_URL)
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




