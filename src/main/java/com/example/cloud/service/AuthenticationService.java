package com.example.cloud.service;

import com.example.cloud.model.Credential;
import com.example.cloud.model.User;

public interface AuthenticationService {
    User findUserByCredentialLogin(String login);

    User findUserByCredential(Credential credential);
}
