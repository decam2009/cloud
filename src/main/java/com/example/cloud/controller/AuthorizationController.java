package com.example.cloud.controller;

import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import com.example.cloud.security.JWTProvider;
import com.example.cloud.service.AuthenticationServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {

    private static final String LOGIN_URL = "/login";
    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String ALLOW_CREDENTIALS_VALUE = "true";
    private final AuthenticationServiceImpl authService;
    private final JWTProvider userAuthenticationProvider;

    public AuthorizationController(AuthenticationServiceImpl authService, JWTProvider userAuthenticationProvider) {
        this.authService = authService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @PostMapping(value = LOGIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public String authorize(@AuthenticationPrincipal @RequestBody Credential credential) {
        User user = authService.findUserByCredential(credential);
        user.setToken(userAuthenticationProvider.createToken(credential.getLogin()));
        return "{\"auth-token\": " + "\"" + user.getToken() + "\"" + "}";
    }
}
