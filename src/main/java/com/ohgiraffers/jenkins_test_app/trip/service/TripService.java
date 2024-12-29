package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.SelectedRegion;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.respository.SelectedRegionRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    SelectedRegionRepository selectedRegionRepository;

    // 날짜 변환 로직 (공통 메서드로 분리)
    private LocalDate convertStringToDate(String dateString) {
        try {
            if (dateString == null || dateString.isEmpty()) {
                return null;
            }
            String parsedDate = dateString.split("T")[0];
            return LocalDate.parse(parsedDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }


    @Transactional
    public Trip addTrip(TripDTO trip) {
        if (trip == null || trip.getUserId() == null || trip.getTitle() == null || trip.getRegions() == null) {
            throw new IllegalArgumentException("Invalid TripDTO input");
        }


        // String -> LocalDate 변환
        LocalDate startDate = convertStringToDate(trip.getStartDate());
        LocalDate endDate = convertStringToDate(trip.getEndDate());

        Trip newTrip = new Trip();
        newTrip.setUserId(Integer.parseInt(trip.getUserId()));
        newTrip.setTitle(trip.getTitle());
        newTrip.setStartDate(startDate);
        newTrip.setEndDate(endDate);
        newTrip.setChattingId(null);

        try {
            Trip savedTrip = tripRepository.save(newTrip);

            for (String region : trip.getRegions()) {
                SelectedRegion selectedRegion = new SelectedRegion(savedTrip.getId(), region, savedTrip);
                selectedRegionRepository.save(selectedRegion);
            }
            return savedTrip;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    public Optional<Trip> selectTrip(Integer id) {

        if(Objects.isNull(id)){
            return Optional.empty();
        }

        Optional<Trip> trip = tripRepository.findById(id);


        return trip;
    }
}
