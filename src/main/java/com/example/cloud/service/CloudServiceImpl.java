package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import com.example.cloud.repository.CloudRepository;
import com.example.cloud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class CloudServiceImpl implements CloudService {
    private final UserRepository userRepository;

    CloudRepository repo;

    public CloudServiceImpl(CloudRepository repo,
                            UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    @Override
    public Storage upload() {
        log.info("upload");
        return null;
    }

    @Override
    public void delete(MultipartFile file) {

    }

    @Override
    public Storage download(MultipartFile file) {
        return null;
    }

    @Override
    public Storage edit(MultipartFile file) {
        return null;
    }

    @Override
    public List<Storage> showAll(Integer limit) {
        return null;
    }
}
