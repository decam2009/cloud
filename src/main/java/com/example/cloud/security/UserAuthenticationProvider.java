package com.example.cloud.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import com.example.cloud.service.AuthenticationServiceImpl;
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

        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_DURATION);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(token);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        User user = authenticationService.findUserByCredentialLogin(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateCredentials(Credential credential) {
        User user = authenticationService.findUserByCredential(credential);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
}
