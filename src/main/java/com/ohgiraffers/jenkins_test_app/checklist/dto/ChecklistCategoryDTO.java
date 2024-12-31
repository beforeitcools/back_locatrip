package com.ohgiraffers.jenkins_test_app.checklist.dto;

// 카테고리 추가 및 수정용 DTO

public class ChecklistCategoryDTO {
    private Integer id;
    private Integer tripId;
    private Integer userId;
    private String name;

    public ChecklistCategoryDTO() {
    }

    public ChecklistCategoryDTO(Integer id, Integer tripId, Integer userId, String name) {
        this.id = id;
        this.tripId = tripId;
        this.userId = userId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChecklistCategoryDTO{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }

}










