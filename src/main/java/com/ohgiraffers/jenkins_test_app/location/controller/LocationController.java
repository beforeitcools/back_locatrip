package com.ohgiraffers.jenkins_test_app.location.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import com.ohgiraffers.jenkins_test_app.location.dto.LocationDTO;
import com.ohgiraffers.jenkins_test_app.location.entity.Location;
import com.ohgiraffers.jenkins_test_app.location.entity.LocationFavorite;
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

    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping("insert")
    public ResponseEntity insertLocation(@RequestBody Map<String, Object> placeData) {

        if(Objects.isNull(placeData)){
            return ResponseEntity.status(404).body("장소를 입력해주세요.");
        }

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        placeData.put("userId", authenticatedUser.getId());

        Location result = locationService.addLocation(placeData);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("장소 등록에 실패했습니다.");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("deleteFavorite")
    public ResponseEntity deleteFavorite(@RequestBody Map<String, Object> placeData) {
        System.out.println("placeData = " + placeData);

        if(Objects.isNull(placeData)){
            return ResponseEntity.status(404).body("올바른 값을 전달해주세요.");
        }

        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        placeData.put("userId", authenticatedUser.getId());
        System.out.println("placeData = " + placeData);


        boolean isDeleted = locationService.deleteFavorite(placeData);


        if(!isDeleted){
            return ResponseEntity.status(500).body("해당 카테고리를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok("성공적으로 삭제되었습니다.");

    }


}
