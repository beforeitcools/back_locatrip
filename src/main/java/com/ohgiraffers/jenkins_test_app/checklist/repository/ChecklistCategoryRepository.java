package com.ohgiraffers.jenkins_test_app.checklist.repository;

import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistCategoryRepository extends JpaRepository<ChecklistCategory, Integer> {
}
