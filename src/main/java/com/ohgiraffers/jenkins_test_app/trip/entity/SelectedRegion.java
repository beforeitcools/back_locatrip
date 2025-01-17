package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



@Entity
@Table(name = "selected_region", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trip_id", "region", "order_index"}) // 복합 유니크 조건
})
//@IdClass(SelectedRegionId.class)
public class SelectedRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "trip_id", nullable = false)
    private Integer tripId;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name="order_index", nullable = false)
    private int orderIndex;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Trip tripEntity;

    public SelectedRegion() {}

    public SelectedRegion(Integer id, Integer tripId, String region, int orderIndex, Trip tripEntity) {
        Id = id;
        this.tripId = tripId;
        this.region = region;
        this.orderIndex = orderIndex;
        this.tripEntity = tripEntity;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Trip getTripEntity() {
        return tripEntity;
    }

    public void setTripEntity(Trip tripEntity) {
        this.tripEntity = tripEntity;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Override
    public String toString() {
        return "SelectedRegion{" +
                "Id=" + Id +
                ", tripId=" + tripId +
                ", region='" + region + '\'' +
                ", orderIndex=" + orderIndex +
                ", tripEntity=" + tripEntity +
                '}';
    }
}
