package com.example.cloud.controller;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import com.example.cloud.model.FileListResponse;
import com.example.cloud.service.CloudServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class StorageController {

    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String ALLOW_CREDENTIALS_VALUE = "true";
    private static final String VALUE_MAPPING = "/file";

    CloudServiceImpl cloudService;

    public StorageController(CloudServiceImpl cloudService) {
        this.cloudService = cloudService;
    }

    @PostMapping(value = VALUE_MAPPING)
    @Transactional
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Object> upload(@AuthenticationPrincipal User user,
                                         @RequestParam(value = "filename") String filename,
                                         @RequestBody MultipartFile file) {
        cloudService.upload(user.getLogin(), file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = VALUE_MAPPING)
    @Transactional
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Object> delete(@AuthenticationPrincipal User user,
                                         @RequestParam(value = "filename") String filename) {
        cloudService.delete(filename);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @Transactional
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<List<FileListResponse>> showAll(@AuthenticationPrincipal User user,
                                                          @RequestParam(value = "limit") Integer limit) {
        List<Storage> storages = cloudService.showAllByLimit(limit);
        List<FileListResponse> files = new ArrayList<>(storages.size());

        for (Storage s : storages) {
            FileListResponse file = new FileListResponse(s.getFileName(), s.getSize());
            files.add(file);
        }
        return ResponseEntity.ok(files);
    }
}
