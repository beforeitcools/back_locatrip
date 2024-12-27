package com.ohgiraffers.jenkins_test_app.auth.repository;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


    Users findByUserId(String userId);

    Users findByNickname(String nickname);
}
