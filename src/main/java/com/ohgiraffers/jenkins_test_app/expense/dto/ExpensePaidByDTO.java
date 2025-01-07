package com.ohgiraffers.jenkins_test_app.expense.dto;

import java.math.BigDecimal;

public class ExpensePaidByDTO {
    private Integer userId;
    private BigDecimal amount;

    public ExpensePaidByDTO() {
    }

    public ExpensePaidByDTO(Integer userId, BigDecimal amount) {
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
        return "ExpensePaidByDTO{" +
                "userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
