package com.ohgiraffers.jenkins_test_app.expense.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expense_paid_by")
public class ExpensePaidBy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    private BigDecimal amount;

    public ExpensePaidBy() {
    }

    public ExpensePaidBy(int id, Expense expense, Integer userId, BigDecimal amount) {
        this.id = id;
        this.expense = expense;
        this.userId = userId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
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
}

