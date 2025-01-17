package com.ohgiraffers.jenkins_test_app.checklist.repository;

import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistCategoryRepository extends JpaRepository<ChecklistCategory, Integer> {

    @Query(value = "SELECT t.start_date, t.end_date FROM trip t WHERE t.id = :tripId", nativeQuery = true)
    Object[] findTripDatesByTripId(@Param("tripId") int tripId);

    @Query("SELECT sr.region FROM SelectedRegion sr WHERE sr.tripId = :tripId")
    List<Object[]> findTripIdAndRegionByTripId(@Param("tripId") int tripId);

    Optional<ChecklistCategory> findByTripIdAndUserId(Integer tripId, Integer userId);
}
