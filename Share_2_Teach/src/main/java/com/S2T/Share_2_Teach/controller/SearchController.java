package com.S2T.Share_2_Teach.controller;

import com.S2T.Share_2_Teach.FileEntity;
import com.S2T.Share_2_Teach.FileRepository;
import com.S2T.Share_2_Teach.entity.Analytics;
import com.S2T.Share_2_Teach.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AnalyticsService analyticsService;

    // File search API endpoint
    @GetMapping
    public ResponseEntity<List<FileEntity>> searchFiles(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String userName) {

        List<FileEntity> results = fileRepository.findByNameContainingOrTagsContainingOrTypeContaining(name, tags, type);
        if (userName != null && !userName.isEmpty()) {
            // Save the search query in analytics
            Analytics analytics = new Analytics("Search query: fileName=" + name + ", tags=" + tags + ", fileType=" + type, new Date(), userName);
            analyticsService.saveAnalytics(analytics);
        }

        return ResponseEntity.ok(results);
    }
}
