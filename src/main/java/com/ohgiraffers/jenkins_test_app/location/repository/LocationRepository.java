package com.ohgiraffers.jenkins_test_app.location.repository;


import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByNameAndAddress(String name, String address);

    @Query("SELECT l.id FROM Location l WHERE l.name IN :locationNames")
    List<Integer> findIdByNameList(@Param("locationNames") List<String> locationNames);

    @Query("SELECT l.id FROM Location l WHERE l.name = :locationName")
    Optional<Integer> findIdByName(@Param("locationName") String locationName);

}
