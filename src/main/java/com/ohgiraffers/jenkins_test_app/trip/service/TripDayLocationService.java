package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripDayLocationRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public TripDayLocation addLocationToTripDay(Map<String, Object> data) {

        TripDayLocation tripDayLocation = new TripDayLocation();


        Optional<Integer> locationIdOptional = locationRepository.findIdByName(String.valueOf(data.get("name")));
        if (locationIdOptional.isPresent()) {
            tripDayLocation.setLocationId(locationIdOptional.get());
        }

        Integer tripId = (Integer) data.get("tripId");
        LocalDate date = convertStringToDate((String) data.get("date"));

        // 해당 날짜의 가장 큰 orderIndex 조회
        int maxOrderIndex = tripDayLocationRepository.findMaxOrderIndexByTripIdAndDate(tripId, date);

        // 새로운 장소 추가
        tripDayLocation.setTripId(tripId);
        tripDayLocation.setLocationId(tripDayLocation.getLocationId());
        tripDayLocation.setDate(date);
        tripDayLocation.setOrderIndex(maxOrderIndex + 1);

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



}
