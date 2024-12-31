package com.ohgiraffers.jenkins_test_app.trip.entity;

import java.io.Serializable;
import java.util.Objects;

public class SelectedRegionId implements Serializable {

    private Integer tripId;
    private String region;

    public SelectedRegionId() {}

    public SelectedRegionId(Integer tripId, String region) {
        this.tripId = tripId;
        this.region = region;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, region);
    }


    // equals & hashCode (복합키 비교에 필요)
    @Override
    public boolean equals(Object obj) {
        // 현재 비교할 객체가 현재 객체와 동일한 경우 true
        if (this == obj) return true;
        // 비교할 객체가 null 이거나, 두 객체의 클래스가 다르면 false
        if (obj == null || getClass() != obj.getClass()) return false;
        SelectedRegionId selectedRegionId = (SelectedRegionId) obj;
        return Objects.equals(tripId, selectedRegionId.tripId) && Objects.equals(region, selectedRegionId.region);
    }


}
