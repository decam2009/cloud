package com.example.cloud.security;

import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;
import com.example.cloud.service.AuthenticationServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final AuthenticationServiceImpl authenticationService;
    private static final int TOKEN_DURATION = 3600000; //Время жизни токена. 1 час.

    public UserAuthenticationProvider(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    @CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_DURATION);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        User user = authenticationService.findUserByLogin(login);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateCredentials(Credential credential) {
        User user = authenticationService.findUserByCredential(credential);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
