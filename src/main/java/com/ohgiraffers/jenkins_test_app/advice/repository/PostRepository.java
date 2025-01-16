package com.ohgiraffers.jenkins_test_app.advice.repository;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer>
{}
