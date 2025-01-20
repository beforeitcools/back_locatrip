package com.ohgiraffers.jenkins_test_app.advice.controller;

import com.ohgiraffers.jenkins_test_app.advice.dto.PostDataDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostIdAndLocattionIdDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostsWithMyLocalAreaDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.ValidTripForPostDTO;
import com.ohgiraffers.jenkins_test_app.advice.entity.LocalAdvice;
import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.service.AdviceService;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.service.MypageService;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    @GetMapping("UserLocalArea/{userId}")
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
    @GetMapping("getPosts/{localArea}")
    public ResponseEntity<Map<String, Object>> getPostsData(@PathVariable("localArea") String localArea) {
        Map<String, Object> postsData = new HashMap<>();

        // 프로필 info
        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        // 내지역 포스트 목록
        List<PostsWithMyLocalAreaDTO> postWithMyLocalAreaList = adviceService.getPostWithMyLocalArea(localArea);

        // 전체 포스트 목록
        List<PostDataDTO> allPostDataList = adviceService.getAllPostData();

        // 알림(안 읽은거 있는지)
        boolean unreadAlarmExists = mypageService.getUnreadAlarmExists(authenticatedUser.getId());

        postsData.put("allPosts",allPostDataList);
        postsData.put("postsInMyRegion", postWithMyLocalAreaList);
        postsData.put("unreadAlarmExists", unreadAlarmExists);
        System.out.println(postWithMyLocalAreaList);
        System.out.println(allPostDataList);
        System.out.println(unreadAlarmExists);

        return ResponseEntity.ok(postsData);
    }

    /**
     *  글쓰기 시도시
     * @PathVariable("userId") Integer userId
     * @return boolean 세개이상의 장소가 포함된 여행일정이 있는지, 해당하는 여행리스트
     * */
    @GetMapping("checkValidTrips/{userId}")
    public ResponseEntity<List<ValidTripForPostDTO>> getValidTrips(@PathVariable("userId") Integer userId) {

        // 세개이상의 장소가 포함된 여행일정
        List<ValidTripForPostDTO> tripList = adviceService.getValidTrips(userId);
        System.out.println(tripList);

        return ResponseEntity.ok(tripList);
    }

    /**
     *  첨삭 등록시
     * 저장항 데이터
     * @return 저장 성공 여부
     * */
    @PostMapping("insertAdvice")
    public ResponseEntity<String> getValidTrips(@RequestBody LocalAdvice localAdvice) {
        return ResponseEntity.ok(adviceService.saveAdvice(localAdvice));
    }

    /**
     *  첨삭보기
     * @PathVariable("locationId") int locationId
     * @return location.category, address, name, orderIndex  , loacalAdvice, user
     * */
    @GetMapping("getAdvice")
    public ResponseEntity<Map<String, Object>> getAdviceData(@RequestBody PostIdAndLocattionIdDTO postIdAndLocattionIdDTO) {

        // 해당하는 포스트
        Map<String, Object> adviceData = adviceService.getAdviceData(postIdAndLocattionIdDTO);

        return ResponseEntity.ok(adviceData);
    }


}
