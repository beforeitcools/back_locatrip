package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripDayLocationRepository extends JpaRepository<TripDayLocation, Integer> {

    // 등록 시 장소순서 확인
    @Query("SELECT COALESCE(MAX(t.orderIndex), 0) FROM TripDayLocation t WHERE t.trip = :trip AND t.date = :date")
    int findMaxOrderIndexByTripAndDate(@Param("trip") Trip trip, @Param("date") LocalDate date);


    // 특정 tripId로 TripDayLocation 목록 조회
    @Query("SELECT t FROM TripDayLocation t WHERE t.trip = :trip")
    List<TripDayLocation> findByTrip(@Param("trip") Trip trip);


    @Modifying
    @Transactional
    @Query("DELETE FROM TripDayLocation t WHERE t.id IN :placeId")
    int deleteByIds(@Param("placeId") List<Integer> placeId);

    @Query("SELECT COUNT(*) FROM TripDayLocation t WHERE t.trip = :trip")
    int findCountByTripId(@Param("trip") Trip trip);
}
