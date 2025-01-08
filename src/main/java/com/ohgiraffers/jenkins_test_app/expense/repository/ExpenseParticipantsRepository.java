package com.ohgiraffers.jenkins_test_app.expense.repository;

import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseParticipantsRepository extends JpaRepository<ExpenseParticipants, Integer> {
    @Query("SELECT p.user.id, u.nickname, p.amount " +
            "FROM ExpenseParticipants p JOIN p.user u " +
            "WHERE p.expense.id = :expenseId")
    List<Object[]> findParticipantsWithNickname(@Param("expenseId") int expenseId);

    void deleteByExpenseId(int expenseId);

    @Query("SELECT p.user.id, u.nickname, p.amount " +
            "FROM ExpenseParticipants p JOIN p.user u")
    List<Object[]> findAllParticipantsWithNickname();
}

