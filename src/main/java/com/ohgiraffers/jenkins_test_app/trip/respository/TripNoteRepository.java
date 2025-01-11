package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.TripNote;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TripNoteRepository extends JpaRepository<TripNote, Integer> {

    @Query("SELECT t FROM TripNote t WHERE t.trip = :trip")
    List<TripNote> findByTripId(@Param("trip") Trip trip);

}
