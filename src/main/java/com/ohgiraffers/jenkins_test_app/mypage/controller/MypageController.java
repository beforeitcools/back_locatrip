package com.ohgiraffers.jenkins_test_app.mypage.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/mypage/*")
public class MypageController {

    @Autowired
    MypageService mypageService;

    @Autowired
    private SecurityUtil securityUtil;


    /**마이페이지 메인 스크린 로드시*/
    @GetMapping("main")
    public ResponseEntity<Map<String, Object>> main() {
        Map<String, Object> mypageData = new HashMap<>();

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        mypageData.put("user", authenticatedUser);

        return ResponseEntity.ok(mypageData);

    }

}
