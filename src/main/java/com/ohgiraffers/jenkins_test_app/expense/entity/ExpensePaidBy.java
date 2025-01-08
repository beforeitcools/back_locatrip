package com.ohgiraffers.jenkins_test_app.expense.entity;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Users 엔티티로 변경

    private BigDecimal amount;

    public ExpensePaidBy() {
    }

    public ExpensePaidBy(int id, Expense expense, Users user, BigDecimal amount) {
        this.id = id;
        this.expense = expense;
        this.user = user;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
