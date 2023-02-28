package com.example.cloud.security;

import com.example.cloud.entities.Credential;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final String SIGNIN_URL = "/login";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final UserAuthenticationProvider provider;

    public UsernamePasswordAuthFilter(UserAuthenticationProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (SIGNIN_URL.equals(request.getServletPath()) && HttpMethod.POST.equals((request.getMethod()))) {
            Credential credential = MAPPER.readValue(request.getInputStream(), Credential.class);
            try {
                SecurityContextHolder.getContext().setAuthentication(
                        provider.validateCredentials(credential)
                );
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }
        filterChain.doFilter(request, response);
    }
}
