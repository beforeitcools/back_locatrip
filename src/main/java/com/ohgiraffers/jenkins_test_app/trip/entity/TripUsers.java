package com.ohgiraffers.jenkins_test_app.trip.entity;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import jakarta.persistence.*;

@Entity
@Table(name = "trip_users")
public class TripUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    public TripUsers() {}

    public TripUsers(int id, Trip trip, Users user) {
        this.id = id;
        this.trip = trip;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
