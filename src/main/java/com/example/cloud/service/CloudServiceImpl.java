package com.example.cloud.service;

import com.example.cloud.model.User;
import com.example.cloud.repository.CloudRepository;
import org.springframework.stereotype.Service;

@Service
public class CloudService {

    private final CloudRepository repo;

    public CloudService(CloudRepository repo) {
        this.repo = repo;
    }

    public void authorize (User user){
        repo.findById(user.getUserId());
    }
}
