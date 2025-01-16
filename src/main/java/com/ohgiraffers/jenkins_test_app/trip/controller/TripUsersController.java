package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.trip.entity.TripUsers;
import com.ohgiraffers.jenkins_test_app.trip.service.TripUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("tripUser")
public class TripUsersController {

    @Autowired
    TripUsersService tripUsersService;

    @PostMapping("saveTripUser")
    public ResponseEntity saveTripUser(@RequestBody Map<String, Object> data){

        if(data.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        System.out.println("data = " + data);

        Integer tripId = (Integer) data.get("tripId");
        Integer userId = (Integer) data.get("userId");
        System.out.println("userId = " + userId);

        TripUsers result = tripUsersService.addTripUser(tripId, userId);
        System.out.println("result = " + result);

        if(result == null){
            return ResponseEntity.status(500).build();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("tripId", result.getTrip().getId());
        resultMap.put("userId", result.getUser().getId());

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("isExistTripUser")
    public ResponseEntity isExistTripUser(@RequestBody Map<String, Object> data){

        if(data.isEmpty()){
            return ResponseEntity.status(404).build();
        }

        Integer tripId = (Integer) data.get("tripId");
        Integer userId = (Integer) data.get("userId");
        System.out.println("tripId = " + tripId);
        System.out.println("userId = " + userId);

        TripUsers result = tripUsersService.isExsistTripUser(tripId, userId);

        System.out.println("result = " + result);
        if(result == null){
            return ResponseEntity.ok(false);
        }


        return ResponseEntity.ok(true);
    }
}
