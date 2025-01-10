package com.ohgiraffers.jenkins_test_app.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class MyAdviceSummaryDTO {

    private Integer adviceId;
    private String title;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private String region;
    private Long regionCount;
    private int isSelected;

    public MyAdviceSummaryDTO() {
    }

    public MyAdviceSummaryDTO(Integer adviceId, String title, String nickname, LocalDate startDate, LocalDate endDate, String region, Long regionCount, int isSelected) {
        this.adviceId = adviceId;
        this.title = title;
        this.nickname = nickname;
        this.startDate = startDate;
        this.endDate = endDate;
        this.region = region;
        this.regionCount = regionCount;
        this.isSelected = isSelected;
    }

    public Integer getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(Integer adviceId) {
        this.adviceId = adviceId;
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

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "MyAdviceSummaryDTO{" +
                "adviceId=" + adviceId +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", region='" + region + '\'' +
                ", regionCount=" + regionCount +
                ", isSelected=" + isSelected +
                '}';
    }
}
