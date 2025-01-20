package com.ohgiraffers.jenkins_test_app.advice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdvicesWithUserInfoDTO {
    private Integer localAdviceId;
    private String contents;
    private Integer userId;
    private String profilePic;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    public AdvicesWithUserInfoDTO() {
    }

    public AdvicesWithUserInfoDTO(Integer localAdviceId, String contents, Integer userId, String profilePic, String nickname, LocalDateTime createdAt) {
        this.localAdviceId = localAdviceId;
        this.contents = contents;
        this.userId = userId;
        this.profilePic = profilePic;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public Integer getLocalAdviceId() {
        return localAdviceId;
    }

    public void setLocalAdviceId(Integer localAdviceId) {
        this.localAdviceId = localAdviceId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AdvicesWithUserInfoDTO{" +
                "localAdviceId=" + localAdviceId +
                ", contents='" + contents + '\'' +
                ", userId=" + userId +
                ", profilePic='" + profilePic + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
