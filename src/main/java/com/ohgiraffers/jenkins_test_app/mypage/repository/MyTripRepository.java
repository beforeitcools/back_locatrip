package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummary;
import com.ohgiraffers.jenkins_test_app.mypage.entity.MyTrip;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyTripRepository extends JpaRepository<Trip, Integer> {

    List<MyTrip> findMyTripsByUserId(Integer userId);


    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummary(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(DISTINCT tu.user.id), 
        COUNT(DISTINCT sr.region),
        true
    )
    FROM Trip t
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE t.userId = :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummary> findTripsOwnedByUser(@Param("userId") Integer userId);


    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummary(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(DISTINCT tu.user.id), 
        COUNT(DISTINCT sr.region),
        false
    )
    FROM Trip t
    JOIN TripUsers tuUser ON t.id = tuUser.trip.id
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE tuUser.user.id = :userId AND t.userId != :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummary> findTripsWhereImMember(@Param("userId") Integer userId);
}
