package com.S2T.Share_2_Teach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username,
            @RequestParam("tags") String tags,
            @RequestParam("subject") String subject,
            @RequestParam("grade") String grade) {

        try {
            // Pass all fields to the service layer and process the PDF conversion
            FileEntity storedFile = fileStorageService.storeAndConvertToPDF(file, username, tags, subject, grade, LocalDateTime.now());

            // Return success response
            return ResponseEntity.ok("File uploaded and converted to PDF successfully: " + storedFile.getFileName());

        } catch (IllegalArgumentException e) {
            // Validation error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());

        } catch (IOException e) {
            // Server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and convert file to PDF due to server error");
        }
    }
}
