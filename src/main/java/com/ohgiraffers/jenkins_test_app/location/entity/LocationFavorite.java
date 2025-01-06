package com.ohgiraffers.jenkins_test_app.location.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;

import jakarta.persistence.*;

@Entity
@Table(name = "location_favorite")
@IdClass(LocationFavoriteId.class)
public class LocationFavorite {

    @Id
    @Column(name = "location_id")
    private Integer locationId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Location locationEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Users userEntity;

    public LocationFavorite() {}

    public LocationFavorite(Integer locationId, Integer userId, Location locationEntity, Users userEntity) {
        this.locationId = locationId;
        this.userId = userId;
        this.locationEntity = locationEntity;
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "LocationFavorite{" +
                "locationId=" + locationId +
                ", userId=" + userId +
                ", locationEntity=" + locationEntity +
                ", userEntity=" + userEntity +
                '}';
    }
}
