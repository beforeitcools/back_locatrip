package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "selected_region")
@IdClass(SelectedRegionId.class)
public class SelectedRegion {

    @Id
    @Column(name = "trip_id")
    private Integer tripId;

    @Id
    @Column(name = "region", nullable = false)
    private String region;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Trip tripEntity;

    public SelectedRegion() {}

    public SelectedRegion(Integer tripId, String region, Trip tripEntity) {
        this.tripId = tripId;
        this.region = region;
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

    @Override
    public String toString() {
        return "SelectedRegion{" +
                "tripId=" + tripId +
                ", region='" + region + '\'' +
                ", tripEntity=" + tripEntity +
                '}';
    }
}
