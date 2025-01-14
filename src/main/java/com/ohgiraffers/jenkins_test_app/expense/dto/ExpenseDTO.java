package com.ohgiraffers.jenkins_test_app.expense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpenseDTO {

    private Integer id;
    private Integer tripId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient = OptBoolean.TRUE)
    private LocalDate date;

    private String category;
    private String description;
    private BigDecimal amount;
    private String paymentMethod;

    private List<ExpensePaidByDTO> paidByUsers;
    private List<ExpenseParticipantsDTO> participants;

    public ExpenseDTO() {
    }

    public ExpenseDTO(Expense expense, List<ExpensePaidByDTO> paidByUsers, List<ExpenseParticipantsDTO> participants) {
        this.id = expense.getId();
        this.tripId = expense.getTripId();
        this.date = expense.getDate();
        this.category = expense.getCategory();
        this.description = expense.getDescription();
        this.amount = expense.getAmount();
        this.paymentMethod = expense.getPaymentMethod();
        this.paidByUsers = paidByUsers;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<ExpensePaidByDTO> getPaidByUsers() {
        return paidByUsers;
    }

    public void setPaidByUsers(List<ExpensePaidByDTO> paidByUsers) {
        this.paidByUsers = paidByUsers;
    }

    public List<ExpenseParticipantsDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ExpenseParticipantsDTO> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "ExpenseDTO{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paidByUsers=" + paidByUsers +
                ", participants=" + participants +
                '}';
    }

}
