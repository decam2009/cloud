package com.example.cloud.model;

import lombok.*;
@Data
@AllArgsConstructor
public class FileListResponse {
    private final String filename;
    private final Long size;
}
