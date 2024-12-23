package com.ohgiraffers.jenkins_test_app.user.repository;

import com.ohgiraffers.jenkins_test_app.user.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {


}
