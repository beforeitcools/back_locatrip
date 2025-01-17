package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "trip_day_location")
public class TripDayLocationIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_index")
    private int orderIndex;

    @Column(name="sort_index")
    private int sortIndex;


    public TripDayLocationIndex() {
    }

    public TripDayLocationIndex(Integer id, int orderIndex, int sortIndex) {
        this.id = id;
        this.orderIndex = orderIndex;
        this.sortIndex = sortIndex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    @Override
    public String toString() {
        return "TripDayLocationIndex{" +
                "id=" + id +
                ", orderIndex=" + orderIndex +
                ", sortIndex=" + sortIndex +
                '}';
    }
}
