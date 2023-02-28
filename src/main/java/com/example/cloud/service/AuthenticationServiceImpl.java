package com.example.cloud.service;

import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;
import com.example.cloud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.CharBuffer;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByLogin(String login) {
        User user = userRepo.findUserByLogin(login);
        String encoded = passwordEncoder.encode(CharBuffer.wrap(user.getCredential().getPassword()));
        return passwordEncoder.matches(CharBuffer.wrap(user.getCredential().getPassword()), encoded) ? user : null;
    }

    @Override
    public User findUserByCredential(Credential credential) {
        User user = userRepo.findUserByCredential(credential);
        String encoded = passwordEncoder.encode(CharBuffer.wrap(user.getCredential().getPassword()));
        return passwordEncoder.matches(CharBuffer.wrap(credential.getPassword()), encoded) ? user : null;
    }
}
