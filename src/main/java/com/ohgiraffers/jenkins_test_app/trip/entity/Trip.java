package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "chatting_id")
    private Integer chattingId;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // 순환 참조 방지
    private List<TripNote> tripNotes = new ArrayList<>();

    @OneToMany(mappedBy = "tripEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SelectedRegion> selectedRegions = new ArrayList<>();

    public Trip() {
    }

    public Trip(Integer id, int userId, String title, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt, Integer chattingId, Integer status, List<TripNote> tripNotes, List<SelectedRegion> selectedRegions) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.chattingId = chattingId;
        this.status = status;
        this.tripNotes = tripNotes;
        this.selectedRegions = selectedRegions;
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

    public Integer getChattingId() {
        return chattingId;
    }

    public void setChattingId(Integer chattingId) {
        this.chattingId = chattingId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TripNote> getMemos() {
        return tripNotes;
    }

    public void setMemos(List<TripNote> tripNotes) {
        this.tripNotes = tripNotes;
    }

    public List<SelectedRegion> getSelectedRegions() {
        return selectedRegions;
    }

    public void setSelectedRegions(List<SelectedRegion> selectedRegions) {
        this.selectedRegions = selectedRegions;
    }
}
