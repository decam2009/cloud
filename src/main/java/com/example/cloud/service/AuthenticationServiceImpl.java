package com.example.cloud.service;

import com.example.cloud.exception.AppException;
import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import com.example.cloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User findUserByCredentialLogin(String login) {
        return userRepo.findUserByCredential_Login(login).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
    }
    @Override
    public User findUserByCredential(Credential credential) {
        User user = userRepo.findUserByCredential(credential).orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credential.getPassword()), Arrays.toString(user.getCredential().getPassword())))
        {
            log.debug("User {} authenticated correctly", credential.getLogin());
            return user;
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
