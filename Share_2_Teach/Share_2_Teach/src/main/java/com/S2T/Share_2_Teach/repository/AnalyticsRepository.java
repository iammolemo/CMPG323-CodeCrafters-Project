package com.S2T.Share_2_Teach.repository;

import java.util.List;

import com.S2T.Share_2_Teach.entity.Analytics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {

    @SuppressWarnings("null")
    @Override
    public List<Analytics> findAll();

}
