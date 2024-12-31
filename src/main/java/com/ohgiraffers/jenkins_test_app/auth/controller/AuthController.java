package com.ohgiraffers.jenkins_test_app.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.auth.dto.SignupDTO;
import com.ohgiraffers.jenkins_test_app.auth.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/auth/*")
public class AuthController {

    @Autowired
    private SignupService signupService;

    String backUrl = "http://112.221.66.174:7777";

    @PostMapping("signup")
    public ResponseEntity signup(@RequestPart("signupData") String signupDataJson,
                                 @RequestPart(value = "profileImg", required = false) MultipartFile profileImg){
        // JsonEncode 되어 있는 signupData를 SignupDTO 로 decode
        ObjectMapper objectMapper = new ObjectMapper();
        SignupDTO signupDTO;
        try {
            signupDTO = objectMapper.readValue(signupDataJson, SignupDTO.class);
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
            signupDTO.setProfilePic(backUrl + "/images/user/profilePic/" + savedName);

            try {
                profileImg.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 실패");
            }
        }

        Object result = SignupService.signup(signupDTO);

        if(Objects.isNull(result)){
            return ResponseEntity.status(500).body("회원가입 실패");
        }

    }



}
