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
                   "e.date, e.category, e.amount, e.description, e.id " +
                   "FROM expense e " +
                   "JOIN trip t ON e.trip_id = t.id " +
                   "WHERE e.date BETWEEN t.start_date AND t.end_date " +
                   "AND e.trip_id = :tripId " +
                   "ORDER BY e.date", nativeQuery = true)
    List<Object[]> findExpensesGroupedByTripDays(@Param("tripId") int tripId);

    @Query(value = "SELECT e.category, e.amount, e.description, e.id " +
                    "FROM expense e " +
                    "WHERE e.trip_id = :tripId AND e.date IS NULL", nativeQuery = true)
    List<Object[]> findPreparationExpenses(@Param("tripId") int tripId);

    @Query("SELECT u.id, u.nickname FROM users_signup u " +
            "JOIN TripUsers tu ON u.id = tu.user.id " +
            "WHERE tu.trip.id = :tripId " +
            "UNION " +
            "SELECT u.id, u.nickname FROM users_signup u " +
            "WHERE u.id = (SELECT t.userId FROM Trip t WHERE t.id = :tripId)")
    List<Object[]> findUsersAndTripByTripId(@Param("tripId") int tripId);

    @Query("SELECT sr.region FROM SelectedRegion sr WHERE sr.tripId = :tripId")
    List<Object[]> findTripIdAndRegionByTripId(@Param("tripId") int tripId);

    @Query(value = "SELECT t.start_date, t.end_date FROM trip t WHERE t.id = :tripId", nativeQuery = true)
    Object[] findTripDatesByTripId(@Param("tripId") int tripId);
}
