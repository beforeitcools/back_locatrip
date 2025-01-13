package com.ohgiraffers.jenkins_test_app.location.service;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavoriteId;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationFavoriteRepository;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationFavoriteRepository locationFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Location addLocationFavorite(Map<String, Object> placeData) {
        // Location 객체 생성 및 저장
        Location location = new Location();
        location.setGoogleId((String) placeData.get("googleId"));
        location.setName((String) placeData.get("name"));
        location.setAddress((String) placeData.get("address"));
        location.setLatitude((Double) placeData.get("latitude"));
        location.setLongitude((Double) placeData.get("longitude"));
        location.setCategory((String) placeData.get("category"));

        System.out.println("placeData = " + placeData);

        // 장소 중복 확인
        Optional<Location> existingLocation = locationRepository.findByGoogleId(location.getGoogleId());

        if (existingLocation.isPresent()) {
            location = existingLocation.get();  // 기존 장소 사용
        } else {
            location = locationRepository.save(location);  // 새 장소 저장
        }

        // 사용자 조회
        Integer userId = (Integer) placeData.get("userId");
        Optional<Users> userOptional = userRepository.findById(userId);


        if (userOptional.isEmpty()) {
            System.out.println("여기 오나????????");
            System.out.println(userOptional);
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

    @Transactional
    public boolean deleteFavorite(String googleId, Integer userId) {

        if(googleId == null || userId == null){
            return false;
        }

        Location location = new Location();


        // 장소 중복 확인
        /*Optional<Location> existingLocation = locationRepository.findByNameAndAddress(
                location.getName(), location.getAddress()
        );*/
        Optional<Location> existingLocation = locationRepository.findByGoogleId(googleId);

        Integer locationId = existingLocation.isPresent() ? existingLocation.get().getId() : 0;


        // 사용자 조회
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


    public List<Map<String, Boolean>> selectFavorites(List<String> locationNameList, Integer userId) {

        List<Map<String, Boolean>> resultList = new ArrayList<>();

        if (locationNameList.isEmpty() || userId == null) {
            return Collections.emptyList();
        }

        List<Integer> locationIds = locationRepository.findIdByNameList(locationNameList);

        if (locationIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<LocationFavorite> favorites = locationFavoriteRepository.findByLocationIdInAndUserId(locationIds, userId);


        for (LocationFavorite favorite : favorites) {
            Map<String, Boolean> resultMap = new HashMap<>();
            String locationName = favorite.getLocationEntity().getName();
//            System.out.println("locationName = " + locationName);
            resultMap.put(locationName, true);
            resultList.add(resultMap);
            System.out.println("resultList = " + resultList);
        }

        return resultList;
    }

    public Map<String, Boolean> selectSpecificFavorite(String locationName, Integer userId) {
        Map<String, Boolean> resultMap = new HashMap<>();

        // locationName이나 userId가 null이면 false 반환
        if(locationName == null || userId == null) {
            resultMap.put(locationName != null ? locationName : "unknown", false);
            System.out.println("resultMap = " + resultMap);
            return resultMap;
        }

        Optional<Integer> locationIdOpt = locationRepository.findIdByName(locationName);

        if (locationIdOpt.isEmpty()) {
            resultMap.put(locationName, false);
            System.out.println("장소 없음: " + locationName);
            return resultMap;
        }

        Integer locationId = locationIdOpt.get();
        System.out.println("locationId = " + locationId);

        // 즐겨찾기 상태 확인
        boolean isFavorite = locationFavoriteRepository.findByLocationIdAndUserId(locationId, userId).isPresent();
        resultMap.put(locationName, isFavorite);

        System.out.println("즐겨찾기 상태: " + (isFavorite ? "저장됨" : "저장 안됨") + " for " + locationName);
        return resultMap;
    }
}
