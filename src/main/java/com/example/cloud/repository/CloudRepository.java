package com.example.cloud.repository;

import com.example.cloud.entities.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CloudRepository extends JpaRepository<Storage, Long> {
    void deleteByFileName (String filename);
    Storage findStorageByFileName (String filename);

    @Query("SELECT s FROM Storage s")
    List<Storage> findAllByLimit (Pageable pageable);
}
