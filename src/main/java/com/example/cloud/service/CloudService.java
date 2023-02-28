package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudService {
    Storage upload ();
    void delete (MultipartFile file);
    Storage download (MultipartFile file);
    Storage edit (MultipartFile file);
    List<Storage> showAll (Integer limit);
}
