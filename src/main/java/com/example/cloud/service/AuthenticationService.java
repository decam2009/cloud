package com.example.cloud.service;

import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;

public interface AuthenticationService {
    User findUserByLogin(String login);

    User findUserByCredential(Credential credential);
}
