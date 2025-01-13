package com.ohgiraffers.jenkins_test_app.expense.repository;

import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensePaidByRepository extends JpaRepository<ExpensePaidBy, Integer> {
    void deleteByExpenseId(int expenseId);

    @Query("SELECT pb.user.id, u.nickname, u.profilePic, pb.amount " +
            "FROM ExpensePaidBy pb JOIN pb.user u " +
            "WHERE pb.expense.id = :expenseId")
    List<Object[]> findPaidByUsersWithNickname(@Param("expenseId") int expenseId);

    @Query("SELECT pb.user.id, u.nickname, pb.amount " +
            "FROM ExpensePaidBy pb JOIN pb.user u")
    List<Object[]> findAllPaidByWithNickname();

}
