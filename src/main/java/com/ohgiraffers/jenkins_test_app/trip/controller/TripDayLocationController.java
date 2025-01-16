package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocationIndex;
import com.ohgiraffers.jenkins_test_app.trip.service.TripDayLocationService;
import com.ohgiraffers.jenkins_test_app.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ohgiraffers.jenkins_test_app.trip.common.ConvertStringToDate.convertStringToDate;

@Controller
@RequestMapping("tripDay")
public class TripDayLocationController {

    @Autowired
    private TripDayLocationService tripDayLocationService;

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("saveTripDayLocation")
    public ResponseEntity saveTripDayLocation(@RequestBody Map<String, Object> data) {

        if(data.isEmpty()){
            return ResponseEntity.status(404).body("등록할 내용을 입력해주세요.");
        }
        System.out.println("data = " + data);


        TripDayLocation tripDayLocation = tripDayLocationService.addTripDayLocation(data);
        if(tripDayLocation == null){
            return ResponseEntity.status(500).body("등록에 실패했습니다.");
        }
        System.out.println("tripDayLocation = " + tripDayLocation);
        return ResponseEntity.ok(tripDayLocation);
    }

    @GetMapping("selectTripDayLocation/{tripId}")
    public ResponseEntity selectTripDayLocation(@PathVariable(name="tripId") Integer tripId) {
        System.out.println("백까진 와");

        System.out.println("!tripId = " + tripId);
        List<TripDayLocation> tripDayLocation = tripDayLocationService.selectTripDayLocation(tripId);
        if(tripDayLocation == null){
            return ResponseEntity.status(500).body("조회에 실패했습니다.");
        }
        System.out.println("!결과보낸다 !tripDayLocation = " + tripDayLocation);
        return ResponseEntity.ok(tripDayLocation);
    }

    @PostMapping("saveTripDayIndex")
    public ResponseEntity saveTripDayIndex(@RequestBody List<Map<String, Object>> data) {
        if(data.isEmpty()){
            return ResponseEntity.status(404).body("등록할 내용을 입력해주세요.");
        }

        List<TripDayLocationDTO> tempDTOList = new ArrayList<>();
        for(Map<String, Object> dataMap : data){
            TripDayLocationDTO tripDayLocationDTO = new TripDayLocationDTO();
            tripDayLocationDTO.setId((Integer) dataMap.get("id"));
            tripDayLocationDTO.setOrderIndex((Integer) dataMap.get("orderIndex"));
            tripDayLocationDTO.setSortIndex((Integer) dataMap.get("sortIndex"));

            tempDTOList.add(tripDayLocationDTO);
        }


        List<TripDayLocationIndex> tripDayLocations= tripDayLocationService.insertTripDayIndex(tempDTOList);
        System.out.println("결과 잘나왔나?" + tripDayLocations);

        if(tripDayLocations == null){
            return ResponseEntity.status(500).body("저장에 실패했습니다.");
        }

        return ResponseEntity.ok(tripDayLocations);
    }


    @PostMapping("deleteTripDay")
    public ResponseEntity deleteMemo(@RequestBody List<Integer> placeId) {
        if(placeId.isEmpty()){
            return ResponseEntity.status(404).body("삭제할 내용을 입력해주세요.");
        }

        boolean result = tripDayLocationService.deleteTripDay(placeId);

        if(!result){
            return ResponseEntity.status(500).body("삭제에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("getTripDayCount/{tripId}")
    public ResponseEntity getTripDayCount(@PathVariable(name="tripId") Integer tripId) {
        if(tripId == null){
            return ResponseEntity.status(404).body("tripId를 입력해주세요.");
        }

        Integer result = tripDayLocationService.getTripDayCount(tripId);
        if(result == null){
            return ResponseEntity.status(500).body("트립데이 개수 조회에 실패했습니다.");
        }
        return ResponseEntity.ok(result);
    }
}
