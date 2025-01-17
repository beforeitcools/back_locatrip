package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

    @Query("SELECT l FROM Trip l WHERE l.id = :id AND l.status = 1")
    Optional<Trip> findActiveTripById(Integer id);

    @Query("""
    SELECT t
    FROM Trip t
    JOIN TripDayLocation td ON t.id = td.trip.id
    WHERE t.userId = :userId AND t.status = 1
    GROUP BY t.id
    HAVING COUNT(td.id) >= 3
    """)
    List<Trip> getValidTripsWithMoreThanThreeLocations(Integer userId);
}
