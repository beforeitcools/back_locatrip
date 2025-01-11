package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.SelectedRegion;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.respository.SelectedRegionRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import com.ohgiraffers.jenkins_test_app.trip.common.ConvertStringToDate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

import static com.ohgiraffers.jenkins_test_app.trip.common.ConvertStringToDate.convertStringToDate;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    SelectedRegionRepository selectedRegionRepository;


    @Transactional
    public Trip addTrip(TripDTO trip) {
        if (trip == null || trip.getTitle() == null || trip.getRegions() == null) {
            throw new IllegalArgumentException("Invalid TripDTO input");
        }

        // String -> LocalDate 변환
        LocalDate startDate = convertStringToDate(trip.getStartDate());
        LocalDate endDate = convertStringToDate(trip.getEndDate());

        Trip newTrip = new Trip();
        newTrip.setUserId(trip.getUserId());
        newTrip.setTitle(trip.getTitle());
        newTrip.setStartDate(startDate);
        newTrip.setEndDate(endDate);
        newTrip.setChattingId(null);
        newTrip.setStatus(1);


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
        System.out.println("i!d = " + id);
        Optional<Trip> trip = tripRepository.findActiveTripById(id);
        System.out.println("trip = " + trip);

        return trip;
    }
}
