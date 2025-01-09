package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.mypage.entity.MyTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyTripRepository extends JpaRepository<MyTrip, Integer> {

    List<MyTrip> findMyTripsByUserId(Integer userId);

}
