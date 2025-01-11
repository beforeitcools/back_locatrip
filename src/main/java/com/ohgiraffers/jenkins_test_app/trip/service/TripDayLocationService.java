package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.location.dto.LocationDTO;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
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
    TripRepository tripRepository;
    @Autowired
    TripDayLocationRepository tripDayLocationRepository;

    /**날짜별 장소 추가*/
    @Transactional
    public TripDayLocation addTripDayLocation(Map<String, Object> data) {


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

        // Trip 엔티티 조회
        Integer tripId = (Integer) data.get("tripId");
        Optional<Trip> trip = tripRepository.findById(tripId);
        if (!trip.isPresent()) {
            throw new IllegalArgumentException("Trip not found for ID: " + tripId);
        }

        LocalDate date = convertStringToDate((String) data.get("date"));
        // 해당 날짜의 가장 큰 orderIndex 조회
        int maxOrderIndex = tripDayLocationRepository.findMaxOrderIndexByTripAndDate(trip.get(), date);
        System.out.println("maxOrderIndex = " + maxOrderIndex);

        TripDayLocation tripDayLocation = new TripDayLocation();
        tripDayLocation.setTrip(trip.get());
        tripDayLocation.setLocation(location);
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


        // 장소 삭제
        tripDayLocationRepository.delete(tripDayLocation);

        // 순서 재정렬
        tripDayLocationRepository.shiftOrderIndexAfterDeletion(tripDayLocation.getTrip(), date, deletedOrderIndex);
    }


    /**조회*/
    public List<TripDayLocation> selectTripDayLocation(Integer tripId) {

        if (tripId == null) {
            return null;
        }

        Optional<Trip> trip = tripRepository.findById(tripId);
        System.out.println("trip = " + trip);
        if (trip.isPresent()) {
            return tripDayLocationRepository.findByTrip(trip.get());
        }

        return null;
    }
}
