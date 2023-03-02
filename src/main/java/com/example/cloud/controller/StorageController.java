package com.example.cloud.controller;

import com.example.cloud.entities.User;
import com.example.cloud.service.CloudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
public class StorageController {

    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String ALLOW_CREDENTIALS_VALUE = "true";

    CloudServiceImpl cloudService;

    public StorageController(CloudServiceImpl cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping(value = "/file")
    @CrossOrigin(origins = CROSS_ORIGIN,  allowCredentials =  ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Object> upload(@AuthenticationPrincipal User user,
                                       @RequestParam (value = "filename") String filename,
                                       @RequestBody MultipartFile file)  throws IOException {
        cloudService.upload(user.getLogin(), file);
        return ResponseEntity.ok().build();
    }

}
