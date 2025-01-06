package com.ohgiraffers.jenkins_test_app.location.controller;

import com.ohgiraffers.jenkins_test_app.location.dto.LocationDTO;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.service.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private LocationService locationService;


    @PostMapping("insert")
    public ResponseEntity insertLocation(@RequestBody Map<String, Object> placeData) {

        if(Objects.isNull(placeData)){
            return ResponseEntity.status(404).body("장소를 입력해주세요.");
        }

        Location result = locationService.addLocation(placeData);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("장소 등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }
}
