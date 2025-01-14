package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.entity.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Integer> {


    boolean existsByIsReadAndUserId(int i, Integer userId);

    @Query("SELECT ua FROM UserAlarm ua WHERE ua.userId = :userId")
    List<UserAlarm> findAllByUserId(Integer userId);
}
