package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("trip")
public class TripController {

    @Autowired
    TripService tripService;

    /** 일정 생성 */
    @PostMapping("insert")
    public ResponseEntity addTrip(@RequestBody TripDTO trip) {
        

        if(Objects.isNull(trip)){
            return ResponseEntity.status(404).body("일정 내용을 입력해주세요.");
        }

        Trip result = tripService.addTrip(trip);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("일정 등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }


    /** 일정조회 */
    @GetMapping("select/{id}")
    public ResponseEntity selectTrip(@PathVariable(name = "id") Integer id) {


        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.badRequest().body("잘못된 일정 ID입니다.");
        }

        Optional<Trip> optionalResult = tripService.selectTrip(id);

        if (optionalResult.isEmpty()) {
            return ResponseEntity.status(404).body("일정을 찾을 수 없습니다.");
        }

        Trip result = optionalResult.get();

        return ResponseEntity.ok(result);
    }


}
