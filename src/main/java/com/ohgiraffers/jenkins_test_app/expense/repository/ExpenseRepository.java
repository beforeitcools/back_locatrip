package com.ohgiraffers.jenkins_test_app.expense.repository;

import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query(value = "SELECT DATEDIFF(e.date, t.start_date) + 1 AS day_number, " +
                   "e.date, e.category, e.amount, e.description " +
                   "FROM expense e " +
                   "JOIN trip t ON e.trip_id = t.id " +
                   "WHERE e.date BETWEEN t.start_date AND t.end_date " +
                   "AND e.trip_id = :tripId " +
                   "ORDER BY e.date", nativeQuery = true)
    List<Object[]> findExpensesGroupedByTripDays(@Param("tripId") int tripId);

    @Query(value = "SELECT e.category, e.amount, e.description " +
                    "FROM expense e " +
                    "WHERE e.trip_id = :tripId AND e.date IS NULL", nativeQuery = true)
    List<Object[]> findPreparationExpenses(@Param("tripId") int tripId);
}
