package com.S2T.Share_2_Teach.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchQuery;

    private Date searchDate;

    private String searchedBy;

    // Constructors, Getters, and Setters
    public Analytics() {
    }

    public Analytics(String searchQuery, Date searchDate, String searchedBy) {
        this.searchQuery = searchQuery;
        this.searchDate = searchDate;
        this.searchedBy = searchedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public String getSearchedBy() {
        return searchedBy;
    }

    public void setSearchedBy(String searchedBy) {
        this.searchedBy = searchedBy;
    }
}
