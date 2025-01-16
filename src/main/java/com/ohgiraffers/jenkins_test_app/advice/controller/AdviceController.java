package com.ohgiraffers.jenkins_test_app.advice.controller;

import com.ohgiraffers.jenkins_test_app.advice.service.AdviceService;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
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
@RequestMapping("/advice/*")
public class AdviceController {

    @Autowired
    AdviceService adviceService;

    @Autowired
    MypageService mypageService;

    @Autowired
    SecurityUtil securityUtil;

    /**
     *  유저의 현지인 인증이 유효한지 검사
     * @PathVariable("userId") Integer userId
     * @return Users.localArea, Users.localAreaAuthDate
     * */
    @GetMapping("UserLocalArea")
    public ResponseEntity<Map<String, Object>> checkUserLocalAreaAuthIsValid(@PathVariable("userId") Integer userId) {
        Map<String, Object> localAreaAuthData = new HashMap<>();
        Object result = mypageService.getUserData(userId);

        if(result instanceof Users){
            localAreaAuthData.put("localArea", ((Users) result).getLocalArea());
            localAreaAuthData.put("localAreaAuthDate", ((Users) result).getLocalAreaAuthDate());
        } else {
            localAreaAuthData.put("localArea", null);
            localAreaAuthData.put("localAreaAuthDate", null);
        }
        return ResponseEntity.ok(localAreaAuthData);
    }

    /**
     *  첨삭소 로드시
     * @PathVariable("localArea") String localArea
     * @return 내지역 포스트 목록, 전체 post 목록, 안읽은 알림 boolean
     * */
    @GetMapping("getPosts")
    public ResponseEntity<Map<String, Object>> getPostsData(@PathVariable("localArea") String localArea) {
        Map<String, Object> postsData = new HashMap<>();

        // 프로필 info
        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        // 채택수
        Long selectedAdviceCount = mypageService.getSelectedAdviceCount(authenticatedUser.getId());

        // 알림(안 읽은거 있는지)
        boolean unreadAlarmExists = mypageService.getUnreadAlarmExists(authenticatedUser.getId());

        mypageData.put("user", authenticatedUser);
        mypageData.put("selectedAdviceCount", selectedAdviceCount);
        postsData.put("unreadAlarmExists", unreadAlarmExists);

        return ResponseEntity.ok(mypageData);
    }


}
