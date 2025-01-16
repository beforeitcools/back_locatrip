package com.ohgiraffers.jenkins_test_app.advice.repository;

import com.ohgiraffers.jenkins_test_app.advice.entity.LocalAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdviceRepository extends JpaRepository<LocalAdvice, Integer> {

    @Query("SELECT COUNT(la) FROM LocalAdvice la WHERE la.userId = :userId AND la.isSelected = 1")
    Long countSelectedAdvicesByUser(@Param("userId") Integer userId);

}
