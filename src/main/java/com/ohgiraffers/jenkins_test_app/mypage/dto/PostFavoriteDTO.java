package com.ohgiraffers.jenkins_test_app.mypage.dto;

public class PostFavoriteDTO {

    private Integer postId;
    private Integer userId;

    public PostFavoriteDTO() {
    }

    public PostFavoriteDTO(Integer postId, Integer userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostFavoriteDTO{" +
                "postId=" + postId +
                ", userId=" + userId +
                '}';
    }
}
