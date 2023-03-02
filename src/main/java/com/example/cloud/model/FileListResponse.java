package com.example.cloud.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileListResponse {
    String filename;
    Long size;
}
