package com.S2T.Share_2_Teach.service;

import com.S2T.Share_2_Teach.entity.Analytics;
import com.S2T.Share_2_Teach.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final AnalyticsRepository analyticsRepository;

    @Autowired
    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public void saveAnalytics(Analytics analytics) {
        analyticsRepository.save(analytics);
    }

    public List<Analytics> getAllAnalytics() {
        return analyticsRepository.findAll();
    }
    
    // Additional methods to retrieve specific analytics if needed
}
