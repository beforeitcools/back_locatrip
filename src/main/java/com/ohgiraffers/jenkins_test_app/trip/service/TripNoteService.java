package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripNoteDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocationIndex;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripNote;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripNoteIndex;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripNoteIndexRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripNoteRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripNoteService {
    @Autowired
    private TripNoteRepository tripNoteRepository;
    @Autowired
    private TripNoteIndexRepository tripNoteIndexRepository;
    @Autowired
    private TripRepository tripRepository;


    @Transactional
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
        tripNote.setSortIndex(memoDTO.getSortIndex());
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

    /** 메모 순서 저장 */
    @Transactional
    public List<TripNoteIndex> insertMemoIndex(List<TripNoteDTO> tempDTOList) {
        // DTO를 Entity로 변환
        List<TripNoteIndex> tripNoteEntities = tempDTOList.stream()
                .map(dto -> new TripNoteIndex(
                        dto.getId(),
                        dto.getSortIndex()
                ))
                .collect(Collectors.toList());

        // saveAll로 리스트 저장
        List<TripNoteIndex> tripNoteIndexList = tripNoteIndexRepository.saveAll(tripNoteEntities);
        System.out.println("tripNoteIndexList = " + tripNoteIndexList);
        return tripNoteIndexList;
    }

    /** 메모 삭제 */
    @Transactional
    public boolean deleteMemo(List<Integer> memoId) {
        if (memoId == null) {
            return false;
        }

        int deletedCount = tripNoteRepository.deleteByIds(memoId);

        return deletedCount > 0;
    }
}
