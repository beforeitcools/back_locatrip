package com.ohgiraffers.jenkins_test_app.auth.repository;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {


}
