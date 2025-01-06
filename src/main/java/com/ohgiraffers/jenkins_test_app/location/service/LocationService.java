package com.ohgiraffers.jenkins_test_app.location.service;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationFavoriteRepository;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationFavoriteRepository locationFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Location addLocation(Map<String, Object> placeData) {
        // Location 객체 생성 및 저장
        Location location = new Location();
        location.setName((String) placeData.get("name"));
        location.setAddress((String) placeData.get("address"));
        location.setLatitude((Double) placeData.get("latitude"));
        location.setLongitude((Double) placeData.get("longitude"));
        location.setCategory((String) placeData.get("category"));

        // 장소 중복 확인
        Optional<Location> existingLocation = locationRepository.findByNameAndAddress(
                location.getName(), location.getAddress()
        );

        if (existingLocation.isPresent()) {
            location = existingLocation.get();  // 기존 장소 사용
        } else {
            location = locationRepository.save(location);  // 새 장소 저장
        }

        // 사용자 조회
        Integer userId = (Integer) placeData.get("userId");
        Optional<Users> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        Users user = userOptional.get();

        // LocationFavorite 저장
        LocationFavorite locationFavorite = new LocationFavorite(
                location.getId(), userId, location, user
        );

        // 중복 방지
        if (!locationFavoriteRepository.existsByLocationIdAndUserId(location.getId(), userId)) {
            locationFavoriteRepository.save(locationFavorite);
        }

        return location;
    }

    public boolean deleteFavorite(Map<String, Object> placeData) {

        if(placeData == null){
            return false;
        }

        Location location = new Location();
        location.setName((String) placeData.get("name"));
        location.setAddress((String) placeData.get("address"));

        // 장소 중복 확인
        Optional<Location> existingLocation = locationRepository.findByNameAndAddress(
                location.getName(), location.getAddress()
        );

        Integer locationId = existingLocation.isPresent() ? existingLocation.get().getId() : 0;


        // 사용자 조회
        Integer userId = (Integer) placeData.get("userId");
        Optional<Users> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        

        // 즐겨찾기 존재 여부 확인 및 삭제
        Optional<LocationFavorite> locationFavoriteOptional =
                locationFavoriteRepository.findByLocationIdAndUserId(locationId, userId);

        if (locationFavoriteOptional.isPresent()) {
            locationFavoriteRepository.delete(locationFavoriteOptional.get());
            return true; // 삭제 성공
        }
  
        // 즐겨찾기 없음
        return false;
    }
}
