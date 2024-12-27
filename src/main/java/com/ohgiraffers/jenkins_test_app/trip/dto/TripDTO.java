package com.ohgiraffers.jenkins_test_app.trip.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripDTO {
    private Integer id;
    private int userId;
    private String title;
    private int regionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int chattingId;

    public TripDTO() {
    }

    public TripDTO(Integer id, int userId, String title, int regionId, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt, int chattingId) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.regionId = regionId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chattingId = chattingId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getChattingId() {
        return chattingId;
    }

    public void setChattingId(int chattingId) {
        this.chattingId = chattingId;
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", regionId=" + regionId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", chattingId=" + chattingId +
                '}';
    }
}
