package com.example.cloud.controller;

import com.example.cloud.model.Credential;
import com.example.cloud.model.Token;
import com.example.cloud.model.User;
import com.example.cloud.security.JWTProvider;
import com.example.cloud.service.AuthenticationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class AuthorizationController {

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
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
    public Token login(@AuthenticationPrincipal @RequestBody Credential credential) {
        User user = authService.findUserByCredential(credential);
        user.setToken(userAuthenticationProvider.createToken(credential.getLogin()));
        return new Token(userAuthenticationProvider.createToken(credential.getLogin()));
    }

    @PostMapping(value = LOGOUT_URL)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Token> logout (@RequestBody Token token){
        token = null;
        return ResponseEntity.ok(token);
    }
}
