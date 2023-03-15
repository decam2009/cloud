package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import com.example.cloud.repository.CloudRepository;
import com.example.cloud.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class CloudServiceImpl implements CloudService {
    private final UserRepository userRepo;
    private final CloudRepository cloudRepo;

    public CloudServiceImpl(UserRepository userRepository, CloudRepository cloudRepository) {
        this.userRepo = userRepository;
        this.cloudRepo = cloudRepository;
    }

    @Transactional
    @Override
    public void upload(String login, MultipartFile file) throws IOException {
        User user = userRepo.findUserByLogin(login);
        Storage storage = cloudRepo.findStorageByFileName(file.getOriginalFilename());
        if (storage == null) {
            String filename = file.getOriginalFilename();
            String type = file.getContentType();
            String hash = DigestUtils.md5DigestAsHex(file.getBytes());
            byte[] data = file.getBytes();
            Long size = file.getSize();
            assert type != null;
            assert filename != null;
            cloudRepo.save(Storage.builder()
                    .user(user)
                    .fileName(filename)
                    .type(type)
                    .hash(hash)
                    .size(size)
                    .data(data)
                    .build());
        }
        log.info("User {} created new file", user.getId());
    }

    @Transactional
    @Override
    public void delete(String filename) {
        cloudRepo.deleteByFileName(filename);
    }

    @Transactional
    @Override
    public void download(String filename, HttpServletResponse response) throws IOException {
        Storage storage = cloudRepo.findStorageByFileName(filename);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + storage.getFileName() + "\"");
        response.setContentType(storage.getType());
        InputStream is = new ByteArrayInputStream(storage.getData());
        IOUtils.copy(is, response.getOutputStream());
    }

    @Transactional
    @Override
    public List<Storage> showAllByLimit(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return cloudRepo.findAllByLimit(pageable);
    }
}
