package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

   // Endpoint for file upload
   
@PostMapping("/upload")
   public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("uploadedBy") String uploadedBy) {
       try {
           FileEntity fileEntity = fileStorageService.storeFile(file, uploadedBy, uploadedBy);
           return new ResponseEntity<>(fileEntity, HttpStatus.OK);
       } catch (IOException e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR );
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

    // Approve a file
    @SuppressWarnings("null")
    @PutMapping("/approve/{id}")
    public ResponseEntity<FileEntity> approveFile(@PathVariable Long id) {
        try {
            FileEntity fileEntity = fileStorageService.approveFile(id);
            return new ResponseEntity<>(fileEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

     // Reject a file
     @SuppressWarnings("null")
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
