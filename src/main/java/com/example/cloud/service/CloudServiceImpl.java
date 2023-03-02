package com.example.cloud.service;

import com.example.cloud.entities.Storage;
import com.example.cloud.entities.User;
import com.example.cloud.repository.CloudRepository;
import com.example.cloud.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.cli.Digest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public Storage upload(String login, MultipartFile file) throws IOException {
        User user = userRepo.findUserByLogin(login);
        String path = this.nfsPath
                + "/"
                + user.getHome()
                + "/"
                + file.getOriginalFilename();

       /* File storedFile = new File(path);
        if (!storedFile.getParentFile().exists()) {
            storedFile.getParentFile().mkdir();
        }
        if (storedFile.getCanonicalFile().exists()){
            throw  new InternalError ("Such file already exits");
        }
        storedFile.createNewFile();
        file.transferTo(storedFile);*/
        String filename = file.getOriginalFilename();
        String type = file.getContentType();
        assert filename != null;
        byte[] data = filename.getBytes();
        String hash = DigestUtils.md5DigestAsHex(data);
        assert type != null;
        log.info("User {} created new image", user.getId());
        return cloudRepo.save(new Storage(user, filename, type, hash, data));
    }

    @Override
    public void delete(MultipartFile file) {

    }

    @Override
    public Storage download(MultipartFile file) {
        return null;
    }

    @Override
    public Storage edit(MultipartFile file) {
        return null;
    }

    @Override
    public List<Storage> showAll(Integer limit) {
        return null;
    }
}
