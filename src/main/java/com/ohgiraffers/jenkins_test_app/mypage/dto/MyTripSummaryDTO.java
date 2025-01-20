package com.ohgiraffers.jenkins_test_app.mypage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class MyTripSummaryDTO {

    private Integer tripId;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;
    private Long memberCount;               // 나 빼고
    private List<String> selectedRegionsList;
    private boolean isCreator;        // 여행 만든 유저인지 초대받은 멤버인지(true: creator, false: member)

    public MyTripSummaryDTO() {
    }

    public MyTripSummaryDTO(Integer tripId, String title, LocalDate startDate, LocalDate endDate, Long memberCount, List<String> selectedRegionsList, boolean isCreator) {
        this.tripId = tripId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberCount = memberCount;
        this.selectedRegionsList = selectedRegionsList;
        this.isCreator = isCreator;
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

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }

    public List<String> getSelectedRegionsList() {
        return selectedRegionsList;
    }

    public void setSelectedRegionsList(List<String> selectedRegionsList) {
        this.selectedRegionsList = selectedRegionsList;
    }

    public boolean getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(boolean isCreator) {
        this.isCreator = isCreator;
    }

    @Override
    public String toString() {
        return "MyTripSummaryDTO{" +
                "tripId=" + tripId +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", memberCount=" + memberCount +
                ", selectedRegionsList=" + selectedRegionsList +
                ", isCreator=" + isCreator +
                '}';
    }
}
