package com.example.cloud.security;

import com.example.cloud.entities.Credential;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.io.IOException;

public class UserAuthenticationFilter implements Filter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing to do
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if ("/login".equals(httpReq.getServletPath())
                && HttpMethod.POST.matches(httpReq.getMethod())) {
            Credential credential = MAPPER.readValue(httpReq.getInputStream(), Credential.class);

            securityContext.setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            credential.getLogin(), credential.getPassword()));
        } else {
            String header = httpReq.getHeader(HttpHeaders.AUTHORIZATION);

            if (header != null) {
                String[] authElements = header.split(" ");

                if (authElements.length == 2
                        && "Bearer".equals(authElements[0])) {
                    securityContext.setAuthentication(new PreAuthenticatedAuthenticationToken(authElements[1], null));
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // nothing to do
    }
}
