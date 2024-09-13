package com.S2T.Share_2_Teach;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] data;

    private String fileName;
    private String fileType;
    private String uploadedBy;
    private Date uploadDate;
    private String filePath;
    private boolean isApproved = false; // For moderation
    private String status; // "Pending", "Approved", "Rejected"

    // Constructors, Getters, and Setters

    // Default Constructor (Required for JPA)
    public FileEntity() {
    }

    public FileEntity(Long id, String fileName, String fileType, byte[] data, Date uploadDate, String uploadedBy, String status) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.uploadDate = uploadDate;
        this.uploadedBy = uploadedBy;
        this.status = status; 
    }

     // Getters and Setters
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

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
     



