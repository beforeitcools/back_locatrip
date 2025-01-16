package com.ohgiraffers.jenkins_test_app.trip.entity;

import java.io.Serializable;
import java.util.Objects;

public class TripUsersId implements Serializable {

    private int trip;
    private int user;

    public TripUsersId() {}

    public TripUsersId(int trip, int user) {
        this.trip = trip;
        this.user = user;
    }

    // getter, setter
    public int getTrip() {
        return trip;
    }

    public void setTrip(int trip) {
        this.trip = trip;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripUsersId that = (TripUsersId) o;
        return trip == that.trip && user == that.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trip, user);
    }
}
