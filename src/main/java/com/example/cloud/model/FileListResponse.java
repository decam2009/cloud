package com.example.cloud.model;

import lombok.*;
@Data
@AllArgsConstructor
public class FileListResponse {
    String filename;
    Long size;
}
