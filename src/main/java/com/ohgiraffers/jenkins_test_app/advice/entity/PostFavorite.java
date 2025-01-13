package com.ohgiraffers.jenkins_test_app.advice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavoriteId;
import jakarta.persistence.*;

@Entity
@Table(name = "post_favorite")
@IdClass(PostFavoriteId.class)
public class PostFavorite {

    @Id
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Posts postEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Users userEntity;

    public PostFavorite() {
    }

    public PostFavorite(Integer postId, Integer userId, Posts postEntity, Users userEntity) {
        this.postId = postId;
        this.userId = userId;
        this.postEntity = postEntity;
        this.userEntity = userEntity;
    }

    public PostFavorite(Integer postId, Integer userId) {
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

    public Posts getPostEntity() {
        return postEntity;
    }

    public void setPostEntity(Posts postEntity) {
        this.postEntity = postEntity;
    }

    public Users getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(Users userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "PostFavorite{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", postEntity=" + postEntity +
                ", userEntity=" + userEntity +
                '}';
    }
}
