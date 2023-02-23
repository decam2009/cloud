package com.example.cloud.service;

import com.example.cloud.model.User;
import com.example.cloud.repository.CloudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {
    private final CloudRepository repo;

    public AuthenticationService(CloudRepository repo) {
        this.repo = repo;
    }

    public void authenticate  (User user) {
        repo.findById(user.getUserId());
    }
}
