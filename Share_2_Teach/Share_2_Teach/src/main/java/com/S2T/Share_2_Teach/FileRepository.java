package com.S2T.Share_2_Teach;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    // Find files by status
    List<FileEntity> findByStatus(String status);

    // Search for files by fileName, tags, or fileType
    List<FileEntity> findByNameContainingOrTagsContainingOrTypeContaining(String name, String tags, String type);
}

