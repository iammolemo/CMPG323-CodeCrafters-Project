package com.S2T.Share_2_Teach;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")  // Specifies the table name in the database
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    private LocalDateTime uploadDate;
    private String uploadedBy;
    private String tags;
    private String subject;  // Added subject field
    private String grade;    // Added grade field
    private String status;

     // Default constructor
     public FileEntity() {
    }

    // Parameterized constructor
    public FileEntity(String fileName, String fileType, byte[] data, LocalDateTime uploadDate,
                      String uploadedBy, String tags, String subject, String grade, String status) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.tags = tags;
        this.subject = subject;
        this.grade = grade;
        this.status = status;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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





