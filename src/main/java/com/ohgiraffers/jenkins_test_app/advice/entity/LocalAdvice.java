package com.ohgiraffers.jenkins_test_app.advice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local_advice")
public class LocalAdvice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "advice_num", nullable = false)
    private Integer adviceNum;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "location_id")
    private Integer locationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_selected")
    private Integer isSelected;

    @Column(name = "status", nullable = false)
    private Integer status;

    public LocalAdvice() {
    }

    public LocalAdvice(Integer id, Integer postId, Integer adviceNum, Integer userId, String contents, Integer locationId, LocalDateTime createdAt, Integer isSelected, Integer status) {
        this.id = id;
        this.postId = postId;
        this.adviceNum = adviceNum;
        this.userId = userId;
        this.contents = contents;
        this.locationId = locationId;
        this.createdAt = createdAt;
        this.isSelected = isSelected;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getAdviceNum() {
        return adviceNum;
    }

    public void setAdviceNum(Integer adviceNum) {
        this.adviceNum = adviceNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LocalAdvice{" +
                "id=" + id +
                ", postId=" + postId +
                ", adviceNum=" + adviceNum +
                ", userId=" + userId +
                ", contents='" + contents + '\'' +
                ", locationId=" + locationId +
                ", createdAt=" + createdAt +
                ", isSelected=" + isSelected +
                ", status=" + status +
                '}';
    }
}
