package com.ohgiraffers.jenkins_test_app.expense.dto;

import java.math.BigDecimal;

public class ExpensePaidByDTO {
    private Integer userId;
    private String nickname;
    private String profilePic;
    private BigDecimal amount;


    public ExpensePaidByDTO() {
    }

    public ExpensePaidByDTO(Integer userId, String nickname, String profilePic, BigDecimal amount) {
        this.userId = userId;
        this.nickname = nickname;
        this.profilePic = profilePic;
        this.amount = amount;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ExpensePaidByDTO{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", amount=" + amount +
                '}';
    }
}
