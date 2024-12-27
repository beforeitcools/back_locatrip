package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Objects;

@RestController
@RequestMapping("trip")
public class TripController {

    @Autowired
    TripService tripService;

    /** 일정 생성 */
    @PostMapping("insert")
    public ResponseEntity addTrip(@RequestBody TripDTO trip) {

        System.out.println("백 도착");
        
        System.out.println("trip = " + trip);
        

        if(Objects.isNull(trip)){
            return ResponseEntity.status(404).body("일정 내용을 입력해주세요.");
        }

        Trip result = tripService.addTrip(trip);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("일정 등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }


}
