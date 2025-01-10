package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TripDayLocationRepository extends JpaRepository<TripDayLocation, Integer> {

    // 등록 시 장소순서 확인
    @Query("SELECT COALESCE(MAX(t.orderIndex), 0) FROM TripDayLocation t WHERE t.tripId = :tripId AND t.date = :date")
    int findMaxOrderIndexByTripIdAndDate(Integer tripId, LocalDate date);

    // 지우는 숫자보다 큰 숫자는 지워짐
    @Modifying
    @Query("UPDATE TripDayLocation t SET t.orderIndex = t.orderIndex - 1 WHERE t.tripId = :tripId AND t.date = :date AND t.orderIndex > :deletedOrderIndex")
    void shiftOrderIndexAfterDeletion(Integer tripId, LocalDate date, Integer deletedOrderIndex);


}
