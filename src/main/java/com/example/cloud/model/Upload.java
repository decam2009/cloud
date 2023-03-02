package com.example.cloud.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Upload {
    private MultipartFile file;
    private String hash;

    public Upload(MultipartFile file, String hash) {
        this.file = file;
        this.hash = hash;
    }
}
