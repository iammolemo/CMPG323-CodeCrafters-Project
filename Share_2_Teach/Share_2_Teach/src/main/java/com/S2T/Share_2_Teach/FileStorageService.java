package com.S2T.Share_2_Teach;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
    private final FileRepository fileRepository;

    @Autowired
    public FileStorageService(FileRepository fileRepository) throws IOException {
        this.fileRepository = fileRepository;
        Files.createDirectories(this.fileStorageLocation);
    }

    public String storeFile(MultipartFile file, String uploader) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        // Save file metadata in the database
        FileEntity fileEntity = new FileEntity(fileName, file.getContentType(), uploader, targetLocation.toString());
        fileRepository.save(fileEntity);

        return fileName;
    }

}
