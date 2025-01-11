package com.ohgiraffers.jenkins_test_app.trip.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TripDayLocationDTO {
    private Integer id;
    private Integer tripId;
    private Integer locationId;
    private LocalDate date;
    private LocalTime visitTime;
    private int orderIndex;
    private String memo;
    private Integer expenseId;
    private int dateIndex;

    public TripDayLocationDTO() {
    }

    public TripDayLocationDTO(Integer id, Integer tripId, Integer locationId, LocalDate date, LocalTime visitTime, int orderIndex, String memo, Integer expenseId, int dateIndex) {
        this.id = id;
        this.tripId = tripId;
        this.locationId = locationId;
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

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    @Override
    public String toString() {
        return "TripDayLocationDTO{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", locationId=" + locationId +
                ", date=" + date +
                ", visitTime=" + visitTime +
                ", orderIndex=" + orderIndex +
                ", memo='" + memo + '\'' +
                ", expenseId=" + expenseId +
                ", dateIndex=" + dateIndex +
                '}';
    }
}
