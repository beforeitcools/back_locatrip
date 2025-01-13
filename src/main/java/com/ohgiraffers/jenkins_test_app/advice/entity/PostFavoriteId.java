package com.ohgiraffers.jenkins_test_app.advice.entity;

import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavoriteId;

import java.io.Serializable;
import java.util.Objects;

public class PostFavoriteId implements Serializable {

    private Integer postId;
    private Integer userId;

    public PostFavoriteId() {
    }

    public PostFavoriteId(Integer postId, Integer userId) {
        this.postId = postId;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId);
    }


    // equals & hashCode (복합키 비교에 필요)
    @Override
    public boolean equals(Object obj) {
        // 현재 비교할 객체가 현재 객체와 동일한 경우 true
        if (this == obj) return true;
        // 비교할 객체가 null 이거나, 두 객체의 클래스가 다르면 false
        if (obj == null || getClass() != obj.getClass()) return false;
        PostFavoriteId postFavoriteId = (PostFavoriteId) obj;
        return Objects.equals(postId, postFavoriteId.postId) && Objects.equals(userId, postFavoriteId.userId);
    }
}
