package com.ohgiraffers.jenkins_test_app.advice.dto;

public class PostIdAndLocattionIdDTO {
    private Integer postId;
    private Integer locationId;

    public PostIdAndLocattionIdDTO() {
    }

    public PostIdAndLocattionIdDTO(Integer postId, Integer locationId) {
        this.postId = postId;
        this.locationId = locationId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "PostIdAndLocattionIdDTO{" +
                "postId=" + postId +
                ", locationId=" + locationId +
                '}';
    }
}
