package com.ohgiraffers.jenkins_test_app.location.repository;


import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByNameAndAddress(String name, String address);
}
