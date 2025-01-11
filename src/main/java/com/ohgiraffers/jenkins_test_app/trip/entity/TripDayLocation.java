package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "trip_day_location")
public class TripDayLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Column(name = "visit_time")
    private LocalTime visitTime;

    @Column(name = "order_index")
    private int orderIndex;

    @Column(name = "memo")
    private String memo;

    @Column(name = "expense_id")
    private Integer expenseId;

    @Column(name = "date_index")
    private int dateIndex;

    // 연관관계 매핑
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip; // Trip 엔티티 객체로 참조

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; // Location 엔티티 객체로 참조

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Expense expense; // Expense 엔티티 객체로 참조

    public TripDayLocation() {
    }

    public TripDayLocation(Integer id, LocalDate date, LocalTime visitTime, int orderIndex, String memo, Integer expenseId, int dateIndex) {
        this.id = id;
        this.date = date;
        this.visitTime = visitTime;
        this.orderIndex = orderIndex;
        this.memo = memo;
        this.expenseId = expenseId;
        this.dateIndex = dateIndex;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalTime visitTime) {
        this.visitTime = visitTime;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    @Override
    public String toString() {
        return "TripDayLocation{" +
                "id=" + id +
                ", date=" + date +
                ", visitTime=" + visitTime +
                ", orderIndex=" + orderIndex +
                ", memo='" + memo + '\'' +
                ", expenseId=" + expenseId +
                ", dateIndex=" + dateIndex +
                '}';
    }
}
