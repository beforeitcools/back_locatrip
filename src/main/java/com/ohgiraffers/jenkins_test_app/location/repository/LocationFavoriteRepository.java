package com.ohgiraffers.jenkins_test_app.location.repository;


import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationFavoriteRepository extends JpaRepository<LocationFavorite, Integer> {

    Optional<LocationFavorite> findByLocationIdAndUserId(Integer locationId, Integer userId);

    boolean existsByLocationIdAndUserId(Integer id, Integer userId);

    List<LocationFavorite> findByLocationIdInAndUserId(List<Integer> locationIds, Integer userId);
}
