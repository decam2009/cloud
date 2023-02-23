package com.example.cloud.controller;

import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import com.example.cloud.security.UserAuthenticationProvider;
import com.example.cloud.service.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudController {

    private static final String LOGIN_URL = "/login";
    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String PRODUCES_JSON = "application/json";
    private final AuthenticationServiceImpl authService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public CloudController(AuthenticationServiceImpl authService, UserAuthenticationProvider userAuthenticationProvider) {
        this.authService = authService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @PostMapping(value = LOGIN_URL, produces = PRODUCES_JSON)
    @CrossOrigin (origins = CROSS_ORIGIN, allowCredentials = "true")
    public ResponseEntity<String> authorize (@AuthenticationPrincipal @RequestBody Credential credential) {
        User user = authService.findUserByCredential(credential);
        user.setToken(userAuthenticationProvider.createToken(credential.getLogin()));
        System.out.println("{@auth-token@: " + user.getToken() + "}");
        return ResponseEntity.ok("{\"auth-token\": " + "\"" + user.getToken() + "\"" + "}");
    }
}
