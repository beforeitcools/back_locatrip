package com.ohgiraffers.jenkins_test_app.checklist.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "checklist_category")
public class ChecklistCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer tripId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ChecklistItem> items;

    public ChecklistCategory() {
    }

    public ChecklistCategory(Integer tripId, Integer userId, String name) {
        this.tripId = tripId;
        this.userId = userId;
        this.name = name;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(List<ChecklistItem> items) {
        this.items = items;
    }
}
