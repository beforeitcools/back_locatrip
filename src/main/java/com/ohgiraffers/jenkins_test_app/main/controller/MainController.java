package com.ohgiraffers.jenkins_test_app.main.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.main.service.MainService;
import com.ohgiraffers.jenkins_test_app.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    MainService mainService;

    @Autowired
    MypageService mypageService;

    @Autowired
    SecurityUtil securityUtil;

    @GetMapping("getUserInfo/{hostId}")
    public ResponseEntity getUserInfo(@PathVariable Integer hostId) {

        if(hostId == null){
            return ResponseEntity.badRequest().build();
        }

        Users users = mainService.getUserInfo(hostId);

        if(users == null){
            return ResponseEntity.noContent().build();
        }

        // 프로필 info
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        // 알림(안 읽은거 있는지)
        boolean unreadAlarmExists = mypageService.getUnreadAlarmExists(authenticatedUser.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("unreadAlarmExists", unreadAlarmExists);

        return ResponseEntity.ok(result);
    }
}
