package com.S2T.Share_2_Teach.repository;

import com.S2T.Share_2_Teach.entity.FilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepo extends JpaRepository<FilesEntity, Long> {
    FilesEntity findByName(String name); // Finds file by name
}
