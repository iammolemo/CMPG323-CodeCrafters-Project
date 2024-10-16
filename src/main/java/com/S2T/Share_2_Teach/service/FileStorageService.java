package com.S2T.Share_2_Teach.service;

import com.S2T.Share_2_Teach.entity.FilesEntity;
import com.S2T.Share_2_Teach.repository.FilesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileStorageService {

    private static final long MAX_FILE_SIZE_MB = 20;
    private static final long MAX_FILE_SIZE_BYTES = MAX_FILE_SIZE_MB * 1024 * 1024; // 20MB in bytes

    @Autowired
    private FilesRepo filesRepo;

    // Stores the file after performing validation
    public String storeFile(MultipartFile fileToSave, String subject, String grade, String tags) throws IOException {
        // Validate fields
        if (fileToSave.isEmpty() || subject == null || subject.isEmpty() || grade == null || grade.isEmpty() || tags == null || tags.isEmpty()) {
            return "Required fields (file, subject, grade, tags) cannot be empty.";
        }
        // Validate file size
        if (fileToSave.getSize() > MAX_FILE_SIZE_BYTES) {
            return "File is too large to upload. Maximum allowed size is 20MB.";
        }

        // Save the file
        FilesEntity filesEntity = FilesEntity.builder()
                .name(fileToSave.getOriginalFilename())
                .type(fileToSave.getContentType())
                .fileData(fileToSave.getBytes())
                .subject(subject)
                .grade(grade)
                .tags(tags)
                .build();

        filesEntity = filesRepo.save(filesEntity);

        return filesEntity.getId() != null ? "File uploaded successfully." : "Failed to upload file.";
    }

    // Retrieves a file by its name
    public byte[] getFiles(String name) {
        FilesEntity file = filesRepo.findByName(name);
        return file != null ? file.getFileData() : null;
    }
}
