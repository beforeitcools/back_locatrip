package com.ohgiraffers.jenkins_test_app.location.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
import com.ohgiraffers.jenkins_test_app.location.service.LocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private SecurityUtil securityUtil;

    /**장소 저장/즐겨찾기저장*/
    @PostMapping("insertFavorite")
    public ResponseEntity insertFavorite(@RequestBody Map<String, Object> placeData) {

        if(Objects.isNull(placeData)){
            return ResponseEntity.status(404).body("장소를 입력해주세요.");
        }

        System.out.println("placeData = " + placeData);

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        placeData.put("userId", authenticatedUser.getId());

        Location result = locationService.addLocationFavorite(placeData);
        System.out.println("result!! = " + result);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("장소 등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }

    /**즐겨찾기 취소*/
    @PostMapping("deleteFavorite")
    public ResponseEntity deleteFavorite(@RequestBody String googleId) {

        if(Objects.isNull(googleId)){
            return ResponseEntity.status(404).body("올바른 값을 전달해주세요.");
        }

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        /*placeData.put("userId", authenticatedUser.getId());*/
        System.out.println("googleId = " + googleId);
        Integer userId = authenticatedUser.getId();

        boolean isDeleted = locationService.deleteFavorite(googleId, userId);


        if(!isDeleted){
            return ResponseEntity.status(500).body("좋아요를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok("성공적으로 삭제되었습니다.");

    }

    @GetMapping("favorites")
    public ResponseEntity favorites(@RequestBody List<String> locationNameList) {

        System.out.println("locationNameList = " + locationNameList);
        if(Objects.isNull(locationNameList)){
            return ResponseEntity.status(404).body("올바른 값을 전달해주세요.");
        }
        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        Integer userId = authenticatedUser.getId();
        System.out.println("userId = " + userId);

        List<Map<String, Boolean>> result = locationService.selectFavorites(locationNameList, userId);
        System.out.println("result = " + result);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("좋아요를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("specificFavorites/{locationName}")
    public ResponseEntity specificFavorites(@PathVariable(name="locationName") String locationName) {


        if(Objects.isNull(locationName)){
            return ResponseEntity.status(404).body("올바른 값을 전달해주세요.");
        }

        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        Integer userId = authenticatedUser.getId();


        Map<String, Boolean> result = locationService.selectSpecificFavorite(locationName, userId);
        System.out.println("result = " + result);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("해당 장소에 대한 좋아요를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(result);
    }

}
