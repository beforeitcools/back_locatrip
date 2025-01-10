package com.ohgiraffers.jenkins_test_app.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class MyPostSummaryDTO {

    private Integer postId;
    private String title;
    private String contents;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private String region;
    private Long regionCount;

    public MyPostSummaryDTO() {
    }

    public MyPostSummaryDTO(Integer postId, String title, String contents, LocalDate startDate, LocalDate endDate, String region, Long regionCount) {
        this.postId = postId;
        this.title = title;
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
        this.region = region;
        this.regionCount = regionCount;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(Long regionCount) {
        this.regionCount = regionCount;
    }

    @Override
    public String toString() {
        return "MyPostSummaryDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", region='" + region + '\'' +
                ", regionCount=" + regionCount +
                '}';
    }
}
