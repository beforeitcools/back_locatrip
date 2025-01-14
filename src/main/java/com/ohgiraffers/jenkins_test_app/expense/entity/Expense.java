package com.ohgiraffers.jenkins_test_app.expense.entity;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "trip_id", nullable = false)
    private Integer tripId;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    private String category;

    private String description;

    private BigDecimal amount;

    private String paymentMethod;

    public Expense(int id, Integer tripId, LocalDate date, String category, String description, BigDecimal amount, String paymentMethod) {
        this.id = id;
        this.tripId = tripId;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

//    @ManyToOne
//    @JoinColumn(name = "location_id")
//    private Location location;


    public Expense() {
    }


    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
