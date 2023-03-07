package com.example.cloud.controller;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import com.example.cloud.model.FileListResponse;
import com.example.cloud.service.CloudServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class StorageController {

    private static final String CROSS_ORIGIN = "http://localhost:8080";
    private static final String ALLOW_CREDENTIALS_VALUE = "true";
    private static final String FILE_MAPPING = "/file";
    private static final String LIST_MAPPING = "/list";

    CloudServiceImpl cloudService;

    public StorageController(CloudServiceImpl cloudService) {
        this.cloudService = cloudService;
    }

    @Transactional
    @PostMapping(value = FILE_MAPPING)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Void> upload(@AuthenticationPrincipal User user,
                                       @RequestParam(value = "filename") String filename,
                                       @RequestBody MultipartFile file) throws IOException {
        cloudService.upload(user.getLogin(), file);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping(value = FILE_MAPPING)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Void> delete(@RequestParam(value = "filename") String filename) {
        cloudService.delete(filename);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @GetMapping(LIST_MAPPING)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<List<FileListResponse>> showAll(@RequestParam(value = "limit") Integer limit) {
        List<Storage> storages = cloudService.showAllByLimit(limit);
        List<FileListResponse> result = storages.stream()
                .map(x -> new FileListResponse(x.getFileName(), x.getSize())).toList();
        return ResponseEntity.ok(result);
    }

    @Transactional
    @GetMapping(value = FILE_MAPPING, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @CrossOrigin(origins = CROSS_ORIGIN, allowCredentials = ALLOW_CREDENTIALS_VALUE)
    public ResponseEntity<Void> download(@RequestParam(value = "filename") String filename, HttpServletResponse response) throws IOException {
        cloudService.download(filename, response);
        return ResponseEntity.ok().build();
    }
}
