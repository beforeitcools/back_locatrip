package com.ohgiraffers.jenkins_test_app.trip.respository;

import com.ohgiraffers.jenkins_test_app.trip.entity.TripUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TripUsersRepository extends JpaRepository<TripUsers, Integer> {

    @Query("SELECT t FROM TripUsers t WHERE t.trip.id = :tripId AND t.user.id = :userId")
    TripUsers findByTripIdAndUserId(@Param("tripId") Integer tripId, @Param("userId") Integer userId);

}
