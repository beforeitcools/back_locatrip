package com.ohgiraffers.jenkins_test_app.advice.dto;

import java.util.List;

public class PostsWithMyLocalAreaDTO {

    private Integer postId;
    private String title;
    private List<String> selectedRegionsList;
    private Integer adviceCount;

    public PostsWithMyLocalAreaDTO() {
    }

    public PostsWithMyLocalAreaDTO(Integer postId, String title, List<String> selectedRegionsList, Integer adviceCount) {
        this.postId = postId;
        this.title = title;
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

    public List<String> getSelectedRegionsList() {
        return selectedRegionsList;
    }

    public void setSelectedRegionsList(List<String> selectedRegionsList) {
        this.selectedRegionsList = selectedRegionsList;
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
                ", selectedRegionsList=" + selectedRegionsList +
                ", adviceCount=" + adviceCount +
                '}';
    }
}
