package com.ohgiraffers.jenkins_test_app.location.repository;


import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyFavoriteLocationSummaryDTO;
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


    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyFavoriteLocationSummaryDTO(
        l.googleId,
        l.name,
        l.address,
        true
    )
    FROM Location l
    JOIN LocationFavorite lf ON l.id = lf.locationId 
    WHERE lf.userId = :userId
""")
    List<MyFavoriteLocationSummaryDTO> getMyFavoriteLocationsData(@Param("userId") Integer userId);

}
