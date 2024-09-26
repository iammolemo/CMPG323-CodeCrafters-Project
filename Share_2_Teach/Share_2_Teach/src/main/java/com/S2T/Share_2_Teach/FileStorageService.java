package com.S2T.Share_2_Teach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
    private final FileRepository fileRepository;

    @Autowired
    public FileStorageService(FileRepository fileRepository) throws IOException {
        this.fileRepository = fileRepository;
        Files.createDirectories(this.fileStorageLocation);
    }

    public FileEntity storeFile(MultipartFile file, String uploadedBy, String tags) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setUploadDate(new Date());
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setTags(tags); // set tags during file upload
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
    // All this is for analytics
    // save all docs by a spesific user
    public List<FileEntity> getFilesByUser(String username) {
        return fileRepository.findAll().stream()
                .filter(file -> file.getUploadedBy().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    //  Count the number of files with a specific tag
    public long countFilesByTag(String tag) {
        return fileRepository.findAll().stream()
                .filter(file -> Arrays.asList(file.getTags().split(",")).contains(tag))
                .count();
    }

    // the most frequently used tags
    public List<String> getMostFrequentTags(int limit) {
        Map<String, Long> tagCountMap = fileRepository.findAll().stream()
                .flatMap(file -> Arrays.stream(file.getTags().split(",")))
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));

        return tagCountMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    //  files uploaded within a specific date range
    public List<FileEntity> getFilesWithinDateRange(Date startDate, Date endDate) {
        return fileRepository.findAll().stream()
                .filter(file -> !file.getUploadDate().before(startDate) && !file.getUploadDate().after(endDate))
                .collect(Collectors.toList());
    }

    //  files by status
    public List<FileEntity> getFilesByStatus(String status) {
        return fileRepository.findByStatus(status);
    }
}


