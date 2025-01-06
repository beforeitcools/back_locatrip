package com.ohgiraffers.jenkins_test_app.location.repository;


import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationFavoriteRepository extends JpaRepository<LocationFavorite, Integer> {

    boolean existsByLocationIdAndUserId(Integer id, Integer userId);
}
