package com.ohgiraffers.jenkins_test_app.advice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostDataDTO {

    // post
    private Integer postId;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    // user
    private Integer userId;
    private String nickname;
    private String profilePic;

    // trip
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;

    private List<String> selectedRegionsList;
    private Long adviceCount;

    public PostDataDTO() {
    }

    public PostDataDTO(Integer postId, String title, String content, LocalDateTime createdAt, Integer userId, String nickname, String profilePic, LocalDate startDate, LocalDate endDate, List<String> selectedRegionsList, Long adviceCount) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.selectedRegionsList = selectedRegionsList;
        this.adviceCount = adviceCount;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public List<String> getSelectedRegionsList() {
        return selectedRegionsList;
    }

    public void setSelectedRegionsList(List<String> selectedRegionsList) {
        this.selectedRegionsList = selectedRegionsList;
    }

    public Long getAdviceCount() {
        return adviceCount;
    }

    public void setAdviceCount(Long adviceCount) {
        this.adviceCount = adviceCount;
    }

    @Override
    public String toString() {
        return "PostDataDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", selectedRegionsList=" + selectedRegionsList +
                ", adviceCount=" + adviceCount +
                '}';
    }
}
