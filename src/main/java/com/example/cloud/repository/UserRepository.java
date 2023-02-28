package com.example.cloud.repository;

import com.example.cloud.entities.Credential;
import com.example.cloud.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findUserByLogin (String login);

    User findUserByCredential(Credential credential);
}
