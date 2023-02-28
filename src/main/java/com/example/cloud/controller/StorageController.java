package com.example.cloud.controller;

import com.example.cloud.service.CloudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class StorageController {

    CloudServiceImpl cloudService;

    @PostMapping(value = "/file", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload (@RequestBody MultipartFile file) {
        cloudService.upload();
    }

}
