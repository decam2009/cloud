package com.example.cloud.repository;

import com.example.cloud.model.Credential;
import com.example.cloud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,  String> {

    Optional <User> findUserByCredential_Login (String login);
    Optional <User> findUserByCredential (Credential credential);
}
