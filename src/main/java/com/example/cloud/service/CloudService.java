package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudService {
    void upload (String login, MultipartFile file) throws IOException;
    void delete (String filename);
    void download(String filename, HttpServletResponse response) throws IOException;

    List<Storage> showAllByLimit(Integer limit);
}
