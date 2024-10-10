package com.S2T.Share_2_Teach;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.S2T.Share_2_Teach.FileEntity;  // Import for FileEntity
import com.S2T.Share_2_Teach.FileRepository;  // Import for FileRepository
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.IOUtils; // Import for handling streams
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

    // Method to store files, with PDF conversion if necessary
    public FileEntity storeFile(MultipartFile file, String uploadedBy, String tags, String subject, String grade, LocalDateTime dateCreated) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setUploadDate(dateCreated); // Set LocalDateTime
        fileEntity.setUploadedBy(uploadedBy);
        fileEntity.setTags(tags); // Store tags for each file
        fileEntity.setSubject(subject); // Store subject
        fileEntity.setGrade(grade); // Store grade
        fileEntity.setStatus("Pending");

        // Convert to PDF if not already a PDF
        if (!file.getContentType().equals("application/pdf")) {
            byte[] pdfData = convertToPdf(file);
            fileEntity.setData(pdfData);
            fileEntity.setFileType("application/pdf"); // Update file type to PDF
            fileEntity.setFileName(file.getOriginalFilename() + ".pdf"); // Update file name to .pdf
        }

        return fileRepository.save(fileEntity);
    }

    // Method to store and convert files to PDF
    public FileEntity storeAndConvertToPDF(MultipartFile file, String uploadedBy, String tags, String subject, String grade, LocalDateTime dateCreated) throws IOException {
        return storeFile(file, uploadedBy, tags, subject, grade, dateCreated);
    }

    // Helper method to convert file to PDF
    private byte[] convertToPdf(MultipartFile file) throws IOException {
        Document document = new Document();
        byte[] pdfBytes;

        try (FileOutputStream fos = new FileOutputStream("temp.pdf")) {
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph(new String(file.getBytes()))); // Add the file content to the PDF
            document.close();
            pdfBytes = Files.readAllBytes(Paths.get("temp.pdf")); // Read the created PDF file
        } catch (DocumentException e) {
            throw new IOException("Error converting file to PDF", e);
        }

        // Clean up temp file
        Files.delete(Paths.get("temp.pdf"));

        return pdfBytes;
    }

    // Get a file by its ID
    public Optional<FileEntity> getFile(Long fileId) {
        return fileRepository.findById(fileId);
    }

    // Get all files
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    // Get files by status (pending, approved, etc.)
    public List<FileEntity> getPendingFiles() {
        return fileRepository.findByStatus("Pending");
    }

    // Approve a file
    public FileEntity approveFile(Long fileId) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        file.setStatus("Approved");
        return fileRepository.save(file);
    }

    // Reject a file
    public FileEntity rejectFile(Long fileId) {
        FileEntity file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        file.setStatus("Rejected");
        return fileRepository.save(file);
    }

    // Analytics: Get files uploaded by a specific user
    public List<FileEntity> getFilesByUser(String username) {
        return fileRepository.findAll().stream()
                .filter(file -> file.getUploadedBy().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    // Analytics: Count the number of files with a specific tag
    public long countFilesByTag(String tag) {
        return fileRepository.findAll().stream()
                .filter(file -> Arrays.asList(file.getTags().split(",")).contains(tag))
                .count();
    }

    // Analytics: Get the most frequently used tags
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

    // Analytics: Get files uploaded within a specific date range
    public List<FileEntity> getFilesWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return fileRepository.findAll().stream()
                .filter(file -> !file.getUploadDate().isBefore(startDate) && !file.getUploadDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    // Analytics: Get files by status
    public List<FileEntity> getFilesByStatus(String status) {
        return fileRepository.findByStatus(status);
    }
}
