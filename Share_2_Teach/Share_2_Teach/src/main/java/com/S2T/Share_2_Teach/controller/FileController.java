package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/files")  // Add a base path for all file-related endpoints
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    // Endpoint for file upload with validation for file name, type, subject, grade, and tags
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("subject") String subject,
            @RequestParam("grade") String grade,
            @RequestParam("tags") String tags) {

        try {
            // Store file with additional inputs and checks
            fileStorageService.storeFile(file, uploadedBy, tags, subject, grade, LocalDateTime.now());
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);

        } catch (RuntimeException e) {
            // Handle validation errors and return appropriate error messages
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed due to server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve all files
    @GetMapping("/all")
    public List<FileEntity> getAllFiles() {
        return fileStorageService.getAllFiles();
    }

    // Retrieve pending files for moderation
    @GetMapping("/pending")
    public List<FileEntity> getPendingFiles() {
        return fileStorageService.getPendingFiles();
    }

    // Approve a file by ID
    @PutMapping("/approve/{id}")
    public ResponseEntity<FileEntity> approveFile(@PathVariable Long id) {
        try {
            FileEntity fileEntity = fileStorageService.approveFile(id);
            return new ResponseEntity<>(fileEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Reject a file by ID
    @PutMapping("/reject/{id}")
    public ResponseEntity<FileEntity> rejectFile(@PathVariable Long id) {
        try {
            FileEntity fileEntity = fileStorageService.rejectFile(id);
            return new ResponseEntity<>(fileEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}

