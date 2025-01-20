package com.ohgiraffers.jenkins_test_app.advice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ValidTripForPostDTO {

    private Integer tripId;
    private String title;
    private String selectedRegion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일")
    private LocalDate endDate;

    public ValidTripForPostDTO() {
    }

    public ValidTripForPostDTO(Integer tripId, String title, String selectedRegion, LocalDate startDate, LocalDate endDate) {
        this.tripId = tripId;
        this.title = title;
        this.selectedRegion = selectedRegion;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ValidTripForPostDTO{" +
                "tripId=" + tripId +
                ", title='" + title + '\'' +
                ", selectedRegion='" + selectedRegion + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
