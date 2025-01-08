package com.ohgiraffers.jenkins_test_app.location.entity;

import java.io.Serializable;
import java.util.Objects;

public class LocationFavoriteId implements Serializable {

    private Integer locationId;
    private Integer userId;

    public LocationFavoriteId() {}

    public LocationFavoriteId(Integer locationId, Integer userId) {
        this.locationId = locationId;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, userId);
    }


    // equals & hashCode (복합키 비교에 필요)
    @Override
    public boolean equals(Object obj) {
        // 현재 비교할 객체가 현재 객체와 동일한 경우 true
        if (this == obj) return true;
        // 비교할 객체가 null 이거나, 두 객체의 클래스가 다르면 false
        if (obj == null || getClass() != obj.getClass()) return false;
        LocationFavoriteId locationFavoriteId = (LocationFavoriteId) obj;
        return Objects.equals(locationId, locationFavoriteId.locationId) && Objects.equals(userId, locationFavoriteId.userId);
    }


}
