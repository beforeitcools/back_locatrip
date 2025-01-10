package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.trip.dto.TripDayLocationDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripDayLocation;
import com.ohgiraffers.jenkins_test_app.trip.service.TripDayLocationService;
import com.ohgiraffers.jenkins_test_app.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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


        TripDayLocation tripDayLocation = tripDayLocationService.addLocationToTripDay(data);
        if(tripDayLocation == null){
            return ResponseEntity.status(500).body("등록에 실패했습니다.");
        }
        System.out.println("tripDayLocation = " + tripDayLocation);
        return ResponseEntity.ok(tripDayLocation);
    }
}
