package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO;
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
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(tu.user.id), 
        COUNT(sr.region),
        true
    )
    FROM Trip t
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE t.userId = :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummaryDTO> findTripsOwnedByUser(@Param("userId") Integer userId);


    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(tu.user.id), 
        COUNT(sr.region),
        false
    )
    FROM Trip t
    JOIN TripUsers tuUser ON t.id = tuUser.trip.id
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE tuUser.user.id = :userId AND t.userId != :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummaryDTO> findTripsWhereImMember(@Param("userId") Integer userId);



}
