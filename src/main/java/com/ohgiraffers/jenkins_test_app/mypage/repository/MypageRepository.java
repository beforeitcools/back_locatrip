package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.entity.AdviceEntireForSelectedCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MypageRepository extends JpaRepository<AdviceEntireForSelectedCount, Integer> {



    @Query("SELECT COUNT(la) FROM LocalAdviceForSelectedCount la JOIN la.adviceEntireForSelectedCount ae " +
            "WHERE ae.user.id = :userId AND la.isSelected = 1")
    Long countSelectedAdvicesByUser(@Param("userId") Integer userId);
}
