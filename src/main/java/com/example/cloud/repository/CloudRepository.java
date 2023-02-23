package com.example.cloud.repository;

import com.example.cloud.model.User;
import org.springframework.data.repository.CrudRepository;
public interface CloudRepository extends CrudRepository<User, Long> {
}
