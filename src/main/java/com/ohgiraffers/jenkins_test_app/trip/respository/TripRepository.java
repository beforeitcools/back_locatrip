package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}
