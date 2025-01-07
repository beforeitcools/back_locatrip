package com.ohgiraffers.jenkins_test_app.expense.dto;

import java.math.BigDecimal;

public class ExpenseParticipantsDTO {

    private Integer userId;
    private BigDecimal amount;

    public ExpenseParticipantsDTO() {
    }

    public ExpenseParticipantsDTO(Integer userId, BigDecimal amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ExpenseParticipantsDTO{" +
                "userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
