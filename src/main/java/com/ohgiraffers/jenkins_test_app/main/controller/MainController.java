package com.ohgiraffers.jenkins_test_app.main.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.main.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("getUserInfo/{hostId}")
    public ResponseEntity getUserInfo(@PathVariable Integer hostId) {

        if(hostId == null){
            return ResponseEntity.badRequest().build();
        }


        Users users = mainService.getUserInfo(hostId);


        if(users == null){
            return ResponseEntity.noContent().build();
        }



        return ResponseEntity.ok(users);
    }
}
