package com.ohgiraffers.jenkins_test_app.trip.dto;

import java.time.LocalDate;
import java.util.List;

public class TripDTO {

    private String userId;
    private String title;
    private String startDate; // LocalDate 로 변환할 예정
    private String endDate;
    private List<String> regions;

    public TripDTO() {
    }

    public TripDTO(String userId, String title, String startDate, String endDate, List<String> regions) {
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regions = regions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", regions=" + regions +
                '}';
    }
}
