package com.example.cloud.controller;

import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;
import com.example.cloud.model.Token;
import com.example.cloud.security.UserAuthenticationProvider;
import com.example.cloud.service.AuthenticationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthorizationController {

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String ALLOW_CREDENTIALS_VALUE = "true";
    private final AuthenticationServiceImpl authService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public AuthorizationController(AuthenticationServiceImpl authService, UserAuthenticationProvider userAuthenticationProvider) {
        this.authService = authService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @PostMapping(value = LOGIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public Token login(@AuthenticationPrincipal @RequestBody Credential credential) {
        User user = authService.findUserByCredential(credential);
        user.setToken(userAuthenticationProvider.createToken(credential.getLogin()));
        log.warn(user.getLogin() + " " + user.getToken());
        return new Token(userAuthenticationProvider.createToken(credential.getLogin()));
    }

    @PostMapping(value = LOGOUT_URL)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Void> logout (){
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}