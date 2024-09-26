package com.S2T.Share_2_Teach;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long>{
    List<FileEntity> findByStatus(String status);

    List<FileEntity> findByFileNameContainingOrTagsContainingOrFileTypeContaining(String fileName, String tags, String fileType); //this will be used to allow searching for fileName tags and fileType
}
