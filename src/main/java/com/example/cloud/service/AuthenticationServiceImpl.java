package com.example.cloud.service;

import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import com.example.cloud.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepo, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByCredentialLogin(String login) {
        return userRepo.findUserByCredential_Login(login);
    }

    @Override
    public User findUserByCredential(Credential credential) {
        User user = userRepo.findUserByCredential(credential).orElseThrow();
        String encoded = passwordEncoder.encode(CharBuffer.wrap(user.getCredential().getPassword()));
        return (passwordEncoder.matches(CharBuffer.wrap(credential.getPassword()), encoded) ? user : null);
    }
}
