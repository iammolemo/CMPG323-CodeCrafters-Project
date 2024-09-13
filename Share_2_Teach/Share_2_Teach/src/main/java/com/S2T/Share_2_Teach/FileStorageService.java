package com.S2T.Share_2_Teach;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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

    public FileEntity storeFile(MultipartFile file, String uploadedBy) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setUploadDate(new Date());
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setStatus("Pending");

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFile(Long fileId) {
        return fileRepository.findById(fileId);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public List<FileEntity> getPendingFiles() {
        return fileRepository.findByStatus("Pending");
    }

    public FileEntity approveFile(Long fileId) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        file.setStatus("Approved");
        return fileRepository.save(file);
    }

    public FileEntity rejectFile(Long fileId) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        file.setStatus("Rejected");
        return fileRepository.save(file);
    }

}
