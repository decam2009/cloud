package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import com.example.cloud.repository.CloudRepository;
import com.example.cloud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class CloudServiceImpl implements CloudService {
    @Value("${app.nfs.path:/storage}")
    private String nfsPath;
    private final UserRepository userRepo;
    private final CloudRepository cloudRepo;

    public CloudServiceImpl(UserRepository userRepository, CloudRepository cloudRepository) {
        this.userRepo = userRepository;
        this.cloudRepo = cloudRepository;
    }

    @Override
    public Storage upload(String login, MultipartFile file) {
        User user = userRepo.findUserByLogin(login);
        Storage storage = cloudRepo.findStorageByFileName(file.getOriginalFilename());
        if (storage == null) {
            String filename = file.getOriginalFilename();
            String type = file.getContentType();
            byte[] data = filename.getBytes();
            Long size = file.getSize();
            storage = cloudRepo.save(new Storage(user, filename, type, size, data));
        }
        log.info("User {} created new file", user.getId());
        return storage;
    }

    @Override
    public void delete(String filename) {
        cloudRepo.deleteByFileName(filename);
    }

    @Override
    public Storage download(String filename) {
        return null;
    }

    @Override
    public Storage edit(String filename) {
        return null;
    }


    @Override
    public List<Storage> showAllByLimit(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return cloudRepo.findAllByLimit(pageable);
    }
}
