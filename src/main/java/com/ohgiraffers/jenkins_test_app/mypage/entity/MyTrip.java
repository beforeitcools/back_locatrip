package com.ohgiraffers.jenkins_test_app.mypage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "trip")
public class MyTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    public MyTrip() {
    }

    public MyTrip(Integer id, int userId, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @Override
    public String toString() {
        return "MyTrip{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
