package com.S2T.Share_2_Teach;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "uploadedfiles")  // Maps this class to the "files" table
public class FileEntity {

    // Getters and Setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob  // Large Object annotation for handling file data
    private byte[] data;

    private LocalDateTime uploadDate;
    private String uploadedBy;
    private String tags;
    private String subject;
    private String grade;
    private String status;

    // Default constructor (required by JPA)
    public FileEntity() {}

    // Parameterized constructor
    public FileEntity(String name, String type, byte[] data, LocalDateTime uploadDate,
                      String uploadedBy, String tags, String subject, String grade, String status) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.tags = tags;
        this.subject = subject;
        this.grade = grade;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return name;
    }

    public void setFileName(String name) {
        this.name = name;
    }

    public String getFileType() {
        return type;
    }

    public void setFileType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
