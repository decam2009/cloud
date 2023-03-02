package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudService {
    Storage upload (String login, MultipartFile file) throws IOException;
    void delete (MultipartFile file);
    Storage download (MultipartFile file);
    Storage edit (MultipartFile file);
    List<Storage> showAll (Integer limit);
}
