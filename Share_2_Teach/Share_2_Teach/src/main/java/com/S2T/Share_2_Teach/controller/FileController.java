package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("username") String username) {
        try {
            FileEntity savedFile = fileStorageService.storeFile(file, username);
            return ResponseEntity.ok(savedFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<FileEntity> approveFile(@PathVariable Long id) {
        FileEntity approvedFile = fileStorageService.approveFile(id);
        return ResponseEntity.ok(approvedFile);
    }

    @DeleteMapping("/reject/{id}")
    public ResponseEntity<FileEntity> rejectFile(@PathVariable Long id) {
        FileEntity rejectedFile = fileStorageService.rejectFile(id);
        return ResponseEntity.ok(rejectedFile);
    }

}
