package com.ohgiraffers.jenkins_test_app.advice.dto;

import java.util.List;

public class PostsWithMyLocalAreaDTO {

    private Integer postId;
    private String title;
    private String selectedRegion;
    private Integer adviceCount;

    public PostsWithMyLocalAreaDTO() {
    }

    public PostsWithMyLocalAreaDTO(Integer postId, String title, String selectedRegion, Integer adviceCount) {
        this.postId = postId;
        this.title = title;
        this.selectedRegion = selectedRegion;
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

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public Integer getAdviceCount() {
        return adviceCount;
    }

    public void setAdviceCount(Integer adviceCount) {
        this.adviceCount = adviceCount;
    }

    @Override
    public String toString() {
        return "PostsWithMyLocalAreaDTO{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", selectedRegion=" + selectedRegion +
                ", adviceCount=" + adviceCount +
                '}';
    }
}
