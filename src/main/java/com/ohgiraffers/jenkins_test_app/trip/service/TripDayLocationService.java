package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.location.dto.LocationDTO;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripDayLocationRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.ohgiraffers.jenkins_test_app.trip.common.ConvertStringToDate.convertStringToDate;

@Service
public class TripDayLocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    TripDayLocationRepository tripDayLocationRepository;

    /**날짜별 장소 추가*/
    @Transactional
    public TripDayLocation addTripDayLocation(Map<String, Object> data) {

        TripDayLocation tripDayLocation = new TripDayLocation();
        Location location = new Location();
        location.setGoogleId((String)data.get("googleId"));
        location.setName((String)data.get("name"));
        location.setAddress((String)data.get("address"));
        location.setLatitude((Double) data.get("latitude"));
        location.setLongitude((Double) data.get("longitude"));
        location.setCategory((String) data.get("category"));

        Optional<Location> existingLocation = locationRepository.findByGoogleId(location.getGoogleId());
        if (existingLocation.isPresent()) {
            location = existingLocation.get();  // 기존 장소 사용
        } else {
            location = locationRepository.save(location);  // 새 장소 저장
        }


        Integer tripId = (Integer) data.get("tripId");
        LocalDate date = convertStringToDate((String) data.get("date"));

        // 해당 날짜의 가장 큰 orderIndex 조회
        int maxOrderIndex = tripDayLocationRepository.findMaxOrderIndexByTripIdAndDate(tripId, date);
        System.out.println("maxOrderIndex = " + maxOrderIndex);

        System.out.println(tripId + " " +  tripDayLocation.getLocationId() + " " + date + " " +  maxOrderIndex+1);
        // 새로운 장소 추가
        tripDayLocation.setTripId(tripId);
        tripDayLocation.setLocationId(location.getId());
        tripDayLocation.setDate(date);
        tripDayLocation.setOrderIndex(maxOrderIndex + 1);
        tripDayLocation.setDateIndex((Integer) data.get("dateIndex"));

        TripDayLocation result = tripDayLocationRepository.save(tripDayLocation);
        if(result == null){
            return null;
        }
        return result;
    }


    /**장소순서 변경*/
    @Transactional
    public void updateLocationOrder(Integer tripDayLocationId, int newOrderIndex) {
        TripDayLocation tripDayLocation = tripDayLocationRepository.findById(tripDayLocationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TripDayLocation ID"));

        // 기존 순서 업데이트
        tripDayLocation.setOrderIndex(newOrderIndex);

        tripDayLocationRepository.save(tripDayLocation);
    }


    /**장소삭제*/
    @Transactional
    public void deleteLocationFromTripDay(Integer tripDayLocationId) {
        TripDayLocation tripDayLocation = tripDayLocationRepository.findById(tripDayLocationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid TripDayLocation ID"));

        int deletedOrderIndex = tripDayLocation.getOrderIndex();
        LocalDate date = tripDayLocation.getDate();
        Integer tripId = tripDayLocation.getTripId();

        // 장소 삭제
        tripDayLocationRepository.delete(tripDayLocation);

        // 순서 재정렬
        tripDayLocationRepository.shiftOrderIndexAfterDeletion(tripId, date, deletedOrderIndex);
    }


    /**조회*/
    public List<TripDayLocation> selectTripDayLocation(Integer tripId) {

        if(tripId == null){
            return null;
        }

        List<TripDayLocation> resultList = tripDayLocationRepository.findByTripId(tripId);
        System.out.println("resultList = " + resultList);
        if(resultList == null){
            return null;
        }

        return resultList;
    }
}
