package com.S2T.Share_2_Teach;

import javax.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String uploader;
    private LocalDateTime uploadTime;
    private String filePath;
    private boolean isApproved = false; // For moderation

    // Constructors, Getters, and Setters

    public FileEntity() {}

    public FileEntity(String fileName, String fileType, String uploader, String filePath) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploader = uploader;
        this.uploadTime = LocalDateTime.now();
        this.filePath = filePath;
}
}

