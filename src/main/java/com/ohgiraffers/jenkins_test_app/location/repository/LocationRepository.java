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

    @Query("SELECT l FROM Location l WHERE l.googleId = :googleId")
    Optional<Location> findByGoogleId(@Param("googleId") String googleId);


    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.location.dto.LocationDTO(
        l.name,
        l.address
    )
    FROM Location l
    JOIN TripUsers tuUser ON t.id = tuUser.trip.id
    LEFT JOIN TripUsers tu ON t.id = tu.trip.id
    LEFT JOIN SelectedRegion sr ON t.id = sr.tripEntity.id
    WHERE tuUser.user.id = :userId AND t.userId != :userId AND t.status = 1
    GROUP BY t.id
""")*/
//    List<LocationDTO> getMyFavoriteLocationsData(@Param("userId") Integer userId);

}
