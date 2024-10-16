package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileManagerController {

    @Autowired
    private FileStorageService fileStorageService;

    // File upload endpoint
    @PostMapping("contributor/upload-file")
    public ResponseEntity<String> storeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subject") String subject,
            @RequestParam("grade") String grade,
            @RequestParam("tags") String tags) throws IOException {

        String response = fileStorageService.storeFile(file, subject, grade, tags);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // File retrieval by file name for download
    @GetMapping("/auth/downloadFile/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String name) {
        byte[] fileData = fileStorageService.getFiles(name);

        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String fileExtension = getFileExtension(name);
        MediaType mediaType = getMediaType(fileExtension);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .headers(headers)
                .body(fileData);
    }

    // File retrieval by file name for viewing online
    @GetMapping("/auth/viewFile/{name}")
    public ResponseEntity<byte[]> viewFile(@PathVariable String name) {
        byte[] fileData = fileStorageService.getFiles(name);

        if (fileData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String fileExtension = getFileExtension(name);
        MediaType mediaType = getMediaType(fileExtension);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(fileData);
    }

    // Helper method to determine file extension
    private String getFileExtension(String name) {
        int lastIndexOfDot = name.lastIndexOf('.');
        return (lastIndexOfDot == -1) ? "" : name.substring(lastIndexOfDot + 1);
    }

    // Helper method to determine media type based on file extension
    private MediaType getMediaType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "png":
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "doc":
            case "docx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            case "ppt":
            case "pptx":
                return MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
