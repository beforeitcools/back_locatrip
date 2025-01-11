package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripNoteDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripNote;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripNoteRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripNoteService {
    @Autowired
    private TripNoteRepository tripNoteRepository;
    @Autowired
    private TripRepository tripRepository;


    public TripNote addMemo(TripNoteDTO memoDTO) {
        if(memoDTO == null){
            return null;
        }


        Integer tripId = memoDTO.getTripId();
        if (tripId == null) {
            throw new IllegalArgumentException("Trip ID is required.");
        }


        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Trip ID: " + tripId));


        TripNote tripNote = new TripNote();
        tripNote.setId(memoDTO.getId());
        tripNote.setContent(memoDTO.getContent());
        tripNote.setTrip(trip);
        tripNote.setDateIndex(memoDTO.getDateIndex());
        System.out.println("trip???? = " + trip);

        System.out.println("memo = " + tripNote);

        TripNote result = tripNoteRepository.save(tripNote);
        System.out.println("result = " + result);

        return result;
    }

    public List<TripNote> searchMemo(Integer tripId) {


        if (tripId == null) {
            throw new IllegalArgumentException("Trip ID is required.");
        }


        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Trip ID: " + tripId));

        List<TripNote> resultList = tripNoteRepository.findByTripId(trip);
        System.out.println("resultList2 = " + resultList);
        return resultList;
    }
}
