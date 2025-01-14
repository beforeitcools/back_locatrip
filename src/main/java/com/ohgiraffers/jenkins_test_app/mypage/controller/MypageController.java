package com.ohgiraffers.jenkins_test_app.mypage.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.advice.entity.PostFavorite;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.common.ServerUrlConstants;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.PostFavoriteDTO;
import com.ohgiraffers.jenkins_test_app.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mypage/*")
public class MypageController {

    @Autowired
    MypageService mypageService;

    @Autowired
    private SecurityUtil securityUtil;


    /**마이페이지 메인 스크린 로드시*/
    @GetMapping("main")
    public ResponseEntity<Map<String, Object>> getMyPageData() {
        Map<String, Object> mypageData = new HashMap<>();

        // 프로필 info
        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        // 채택수
        Long selectedAdviceCount = mypageService.getSelectedAdviceCount(authenticatedUser.getId());

        // 알림(안 읽은거 있는지)

        mypageData.put("user", authenticatedUser);
        mypageData.put("selectedAdviceCount", selectedAdviceCount);

        return ResponseEntity.ok(mypageData);
    }

    /**프로필 수정*/
    @PostMapping("updateProfile")
    public ResponseEntity updateProfile(@RequestPart("updatedData") String updatedDataJson,
                                 @RequestPart(value = "profileImg", required = false) MultipartFile profileImg){
        // JsonEncode 되어 있는 signupData를 SignupDTO 로 decode
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        UsersDTO usersDTO;
        byte[] bytes = updatedDataJson.getBytes(StandardCharsets.ISO_8859_1);
        String decodedJson = new String(bytes, StandardCharsets.UTF_8);

        try {
            usersDTO = objectMapper.readValue(decodedJson, UsersDTO.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 회원가입 데이터");
        }

        // 프로필 이미지 서버 실물 경로에 저장(db에 들어갈 이미지 경로 setting)
        // 배포시점과 서버 변경시점에 backUrl 만 변경해주면 된다.(이미 db에 저장된 데이터는 backUrl 경로만 update)
        if(!Objects.isNull(profileImg)){
            String savePath = "C:/locat/profile_pic";
            File fileDir = new File(savePath);
            if(!fileDir.exists()){
                fileDir.mkdirs();
            }

            String originalFileName = profileImg.getOriginalFilename();
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
            String filePath = savePath + "/" + savedName;
            usersDTO.setProfilePic(ServerUrlConstants.BACK_URL + "/images/user/profilePic/" + savedName);

            try {
                profileImg.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 이미지 업로드 실패");
            }
        }

        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        Object result = mypageService.updateProfile(usersDTO, authenticatedUser);

        if(result instanceof Users){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(500).body("프로필 수정 실패 : " + result);
    }


    /**마이페이지 내여행 로드시*/
    @GetMapping("myTrip")
    public ResponseEntity<Map<String, Object>> getMyTripData() {
        Map<String, Object> myTripData = new HashMap<>();

        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        List<MyTripSummaryDTO> myTripList = mypageService.getMyTrips(authenticatedUser.getId());

        List<MyTripSummaryDTO> futureTrips = myTripList.stream()
                .filter(trip -> trip.getStartDate().isAfter(LocalDate.now()))
                .sorted(Comparator.comparing(MyTripSummaryDTO::getStartDate))
                .collect(Collectors.toList());

        List<MyTripSummaryDTO> pastTrips = myTripList.stream()
                .filter(trip -> trip.getStartDate().isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(MyTripSummaryDTO::getStartDate))
                .collect(Collectors.toList());

        System.out.println("controller layer: " + futureTrips);
        System.out.println("controller layer: " + pastTrips);

        myTripData.put("futureTrips", futureTrips);
        myTripData.put("pastTrips", pastTrips);

        return ResponseEntity.ok(myTripData);
    }

    /**마이페이지 내여행 삭제*/
    @PostMapping("deleteTrip/{tripId}")
    public ResponseEntity<String> deleteTrip(@PathVariable("tripId") Integer tripId) {

        try{
            String result = mypageService.deleteTrip(tripId);
            if (result.contains("성공")) {
                return ResponseEntity.ok(result);
            } else return ResponseEntity.status(500).body(result);
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**마이페이지 내저장 로드시*/
    @GetMapping("myFavorite/{userId}")
    public ResponseEntity<Map<String, Object>> getMyFavoriteData(@PathVariable("userId") Integer userId) {

        Map<String, Object> myFavoriteList = mypageService.getMyFavorites(userId);

        return ResponseEntity.ok(myFavoriteList);
    }

    /**마이페이지 내저장게시글 insert*/
    @PostMapping("insertFavoritePost")
    public ResponseEntity insertFavoritePost(@RequestBody PostFavoriteDTO postFavoriteDTO) {

        Object result = mypageService.insertFavoritePost(postFavoriteDTO);

        if(result instanceof PostFavorite){
            return ResponseEntity.ok(result);
        } else return ResponseEntity.status(500).body(result);
    }

    /**마이페이지 내저장게시글 delete*/
    @PostMapping("deleteFavoritePost")
    public ResponseEntity deleteFavoritePost(@RequestBody PostFavoriteDTO postFavoriteDTO) {
        Map<String, String> response = new HashMap<>();

        if(mypageService.deleteFavoritePost(postFavoriteDTO)){
            response.put("message", "게시글 좋아요 삭제 성공");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "저장되어 있지 않은 게시글 입니다.");
            return ResponseEntity.status(500).body(response);
        }
    }


    /**마이페이지 내포스트 로드시*/
    @GetMapping("myPost/{userId}")
    public ResponseEntity<List> getMyTripData(@PathVariable("userId") Integer userId) {

        List<MyPostSummaryDTO> myPostList = mypageService.getMyPosts(userId);

        return ResponseEntity.ok(myPostList);
    }

    /**마이페이지 현지인 인증하기 로드시*/
    @GetMapping("myLocalArea/{userId}")
    public ResponseEntity<Users> getMyLocalAreaAuthData(@PathVariable("userId") Integer userId) {

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        return ResponseEntity.ok(authenticatedUser);
    }

    /**마이페이지 현지인 인증하기 update*/
    @PostMapping("updateMyLocalArea")
    public ResponseEntity updateMyLocalArea(@RequestBody UsersDTO usersDTO) {
        Map<String, String> response = new HashMap<>();
        Object result = mypageService.updateMyLocalArea(usersDTO);

        if(result instanceof Users){
            return ResponseEntity.ok(result);
        } else {
            response.put("message", "현지인 인증 db추가 실패");
            System.out.println("현지인 인증 db추가 실패");
            return ResponseEntity.status(500).body(response);
        }
    }



//    /**마이페이지 내첨삭 로드시*/
//    @GetMapping("myAdvice/{userId}")
//    public ResponseEntity<List> getMyAdviceData(@PathVariable("userId") Integer userId) {
//
//        List<MyAdviceSummaryDTO> myAdviceList = mypageService.getMyAdvices(userId);
//
//        return ResponseEntity.ok(myAdviceList);
//    }
}
