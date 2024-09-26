package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileRepository;
import com.S2T.Share_2_Teach.entity.Analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.S2T.Share_2_Teach.service.AnalyticsService;

import java.util.List;
import java.util.Date;

@RestController //this is used to define a RESTful web controller
@RequestMapping("/api/search") //sets up a base URL for search API
public class SearchController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping //defines an HTTP GET request handler
    public ResponseEntity<List<FileEntity>> searchFiles(
            @RequestParam(required = false) String fileName, // extract query parameters form the request URL
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String fileType,
            @RequestParam String userName) {
        
             
        List<FileEntity> results = fileRepository.findByFileNameContainingOrTagsContainingOrFileTypeContaining(fileName, tags, fileType);
        Analytics analytics = new Analytics("Search query: fileName=" + fileName + ", tags=" + tags + ", fileType=" + fileType, new Date(), userName);
        analyticsService.saveAnalytics(analytics); //save the search 
        return ResponseEntity.ok(results);
    }
}