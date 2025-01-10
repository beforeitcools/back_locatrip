package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import com.ohgiraffers.jenkins_test_app.trip.service.TripDayLocationService;
import com.ohgiraffers.jenkins_test_app.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
}
