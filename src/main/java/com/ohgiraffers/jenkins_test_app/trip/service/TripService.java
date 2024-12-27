package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.SelectedRegion;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.respository.SelectedRegionRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    SelectedRegionRepository selectedRegionRepository;

    // 날짜 변환 로직 (공통 메서드로 분리)
    private LocalDate convertStringToDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        String parsedDate = dateString.split("T")[0];
        return LocalDate.parse(parsedDate);
    }


    @Transactional
    public Trip addTrip(TripDTO trip) {
        if(Objects.isNull(trip)){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // String -> LocalDate 변환
        LocalDate startDate = convertStringToDate(trip.getStartDate());
        LocalDate endDate = convertStringToDate(trip.getEndDate());

        Trip newTrip = new Trip();
        newTrip.setUserId(Integer.parseInt(trip.getUserId()));
        newTrip.setTitle(trip.getTitle());
        newTrip.setStartDate(startDate);
        newTrip.setEndDate(endDate);
        newTrip.setChattingId(null);

        // Trip 저장
        Trip savedTrip = tripRepository.save(newTrip);

        // 선택한 지역 저장
        for (String region : trip.getRegions()) {
            SelectedRegion selectedRegion = new SelectedRegion(savedTrip.getId(), region, savedTrip);
            SelectedRegion result = selectedRegionRepository.save(selectedRegion);
        }

        if(savedTrip == null) {
            return null;
        }
        return savedTrip;
    }
}
