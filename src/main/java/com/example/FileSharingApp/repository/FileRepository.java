package com.example.FileSharingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import com.example.FileSharingApp.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer> {
    List<FileEntity> findByExpiryTimeBefore(LocalDateTime now);
}
