package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.entity.AdviceEntireForSelectedCount;
import com.ohgiraffers.jenkins_test_app.mypage.entity.LocalAdviceForSelectedCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MypageRepository extends JpaRepository<LocalAdviceForSelectedCount, Integer> {

    @Query("SELECT COUNT(la) FROM LocalAdviceForSelectedCount la JOIN la.adviceEntireForSelectedCount ae " +
            "WHERE ae.user.id = :userId AND la.isSelected = 1")
    Long countSelectedAdvicesByUser(@Param("userId") Integer userId);

    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO(
        t.user.nickname, 
        t.title, 
        t.startDate, 
        t.endDate, 
        (SELECT sr.region FROM SelectedRegion sr WHERE sr.trip.id = t.id LIMIT 1),
        (SELECT COUNT(sr.region) FROM SelectedRegion sr WHERE sr.trip.id = t.id),
        la.isSelected
    )
    FROM LocalAdviceForSelectedCount la
    JOIN Posts p ON la.
    JOIN p.trip t
    JOIN Users u ON 
    WHERE la.status = 1 
      AND la.user.id = :userId
""")
    List<MyAdviceSummaryDTO> findMyAdvices(@Param("userId") Integer userId);*/
}
