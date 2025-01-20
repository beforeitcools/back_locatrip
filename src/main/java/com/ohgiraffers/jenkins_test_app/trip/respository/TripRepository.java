package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.advice.dto.ValidTripForPostDTO;
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
    SELECT new com.ohgiraffers.jenkins_test_app.advice.dto.ValidTripForPostDTO(
        t.id,
        t.title,
        sr.region,
        t.startDate,
        t.endDate
    )
    FROM Trip t
    JOIN TripDayLocation td ON t.id = td.trip.id
    JOIN SelectedRegion sr ON sr.tripEntity.id = t.id
    WHERE sr.Id = (SELECT MIN(sr2.Id) FROM SelectedRegion sr2 WHERE sr2.tripEntity.id = t.id)
    AND t.userId = :userId AND t.status = 1
    GROUP BY t.id, sr.region, t.title, t.startDate, t.endDate
    HAVING COUNT(td.id) >= 3
    """)
    List<ValidTripForPostDTO> getValidTripsWithMoreThanThreeLocations(Integer userId);
}
