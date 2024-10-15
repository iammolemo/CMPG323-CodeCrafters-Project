package com.S2T.Share_2_Teach.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;   // Import statement for List
import java.util.Arrays; // Import for utility function to handle string splitting

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uploadedfiles")
@Builder
public class FilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private Long size;    // File size (in bytes)

    @Lob
    private byte[] fileData;    // Actual file data

    private String subject;   // Subject related to the file

    private String grade;     // Grade related to the file

    private String tags;      // Tags for categorization (stored as a comma-separated string)

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateUploaded;

    @PrePersist
    protected void onCreate() {
        this.dateUploaded = LocalDateTime.now();
    }

    // Utility method to convert tag list to comma-separated string
    public void setTagsFromList(List<String> tagList) {
        this.tags = String.join(",", tagList);
    }

    // Utility method to retrieve tags as a list
    public List<String> getTagsAsList() {
        return List.of(this.tags.split(","));
    }
}