package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudService {
    Storage upload (String login, MultipartFile file);
    void delete (String filename);
    Storage download (String filename);
    Storage edit (String filename);
    List<Storage> showAllByLimit(Integer limit);
}
