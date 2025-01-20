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


    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(tu.user.id), 
        GROUP_CONCAT(sr.region),
        true
    )
    FROM Trip t
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE t.userId = :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummaryDTO> findTripsOwnedByUser(@Param("userId") Integer userId);*/

// COUNT(tu.user_id) : trip을 만든 user는 tu에 없어서 빠지니 결과가 맞다(n명과 함께)
    @Query(value = """
    SELECT 
        t.id, 
        t.title, 
        t.start_date,
        t.end_date,
        COUNT(tu.user_id), 
        GROUP_CONCAT(DISTINCT sr.region ORDER BY sr.order_index ASC) AS regions
    FROM trip t
    LEFT JOIN trip_users tu ON t.id = tu.trip_id
    JOIN selected_region sr ON t.id = sr.trip_id
    WHERE t.user_id = :userId AND t.status = 1
    GROUP BY t.id, t.id, t.title, t.start_date, t.end_date
""", nativeQuery = true)
    List<Object[]> findTripsOwnedByUser(@Param("userId") Integer userId);

    // COUNT(tu.user_id) : 결과적으로 맞다 내가 포함되는 대신 trip 만든 유저는 빼고 count 결과적으론 -1 +1 (n명과 함께)
    @Query(value ="""
    SELECT
        t.id,
        t.title,
        t.start_date,
        t.end_date,
        COUNT(tu.user_id),
        GROUP_CONCAT(DISTINCT sr.region ORDER BY sr.order_index ASC) AS regions
    FROM trip t
    LEFT JOIN trip_users tu ON t.id = tu.trip_id
    JOIN selected_region sr ON t.id = sr.trip_id
    WHERE tu.user_id = :userId AND t.user_id != :userId AND t.status = 1
    GROUP BY t.id
""", nativeQuery = true)
    List<Object[]> findTripsWhereImMember(@Param("userId") Integer userId);

    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO(
        t.id, 
        t.title, 
        t.startDate, 
        t.endDate, 
        COUNT(tu.user.id), 
        GROUP_CONCAT(sr.region),
        false
    )
    FROM Trip t
    JOIN TripUsers tuUser ON t.id = tuUser.trip.id
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE tuUser.user.id = :userId AND t.userId != :userId AND t.status = 1
    GROUP BY t.id
""")
    List<MyTripSummaryDTO> findTripsWhereImMember(@Param("userId") Integer userId);*/

}
