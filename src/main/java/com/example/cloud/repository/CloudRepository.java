package com.example.cloud.repository;

import com.example.cloud.entities.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudRepository extends JpaRepository<Storage, Long> {

}
