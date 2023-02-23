package com.example.cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity

public class SecurityConfig implements WebMvcConfigurer {
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private static final String MAPPING_LOGIN_URL = "/login";
    private static final String MAPPING_LOGOUT_URL = "/logout";
    private final String ORIGIN_URL = "http://localhost:8080";


    public SecurityConfig(UserAuthenticationProvider userAuthenticationProvider, UserAuthenticationEntryPoint userAuthenticationEntryPoint) {
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
    }


    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {

        http
                .cors(httpSecurityCorsConfigurer -> {
                    CorsRegistry registry = new CorsRegistry();
                    registry.addMapping(MAPPING_LOGIN_URL)
                            .allowedOrigins(ORIGIN_URL)
                            .allowedMethods(String.valueOf(HttpMethod.POST));
                })
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new UsernamePasswordAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthFilter(userAuthenticationProvider), UsernamePasswordAuthFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, MAPPING_LOGIN_URL, MAPPING_LOGOUT_URL)
                        .permitAll()
                        .anyRequest()
                        .authenticated());

        return http.build();
    }

}


