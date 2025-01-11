package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query("SELECT l FROM Trip l WHERE l.id = :id AND l.status = 1")
    Optional<Trip> findActiveTripById(Integer id);
}
