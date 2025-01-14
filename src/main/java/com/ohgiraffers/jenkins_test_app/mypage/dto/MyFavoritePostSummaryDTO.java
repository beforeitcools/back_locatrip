package com.ohgiraffers.jenkins_test_app.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MyFavoritePostSummaryDTO {

    private Integer postId;
    private String title;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private boolean isFavorite;

    public MyFavoritePostSummaryDTO() {
    }

    public MyFavoritePostSummaryDTO(Integer postId, String title, String nickname, LocalDate startDate, LocalDate endDate, boolean isFavorite) {
        this.postId = postId;
        this.title = title;
        this.nickname = nickname;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFavorite = isFavorite;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "MyFavoritePostSummaryDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
