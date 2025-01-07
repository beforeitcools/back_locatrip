package com.ohgiraffers.jenkins_test_app.expense.repository;

import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensePaidByRepository extends JpaRepository<ExpensePaidBy, Integer> {
    List<ExpensePaidBy> findByExpenseId(Integer expenseId);
    void deleteByExpenseId(int expenseId);
}
