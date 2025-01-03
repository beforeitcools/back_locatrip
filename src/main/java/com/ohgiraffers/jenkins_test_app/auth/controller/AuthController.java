package com.ohgiraffers.jenkins_test_app.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.auth.common.EmailValidator;
import com.ohgiraffers.jenkins_test_app.auth.common.NameValidator;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.service.AuthService;
import com.ohgiraffers.jenkins_test_app.common.AuthConstants;
import com.ohgiraffers.jenkins_test_app.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/auth/*")
public class AuthController {

    String backUrl = "http://112.221.66.174:8082";

    @Autowired
    private AuthService authService;

    /**회원가입*/
    @PostMapping("signup")
    public ResponseEntity signup(@RequestPart("signupData") String signupDataJson,
                                 @RequestPart(value = "profileImg", required = false) MultipartFile profileImg){
        // JsonEncode 되어 있는 signupData를 SignupDTO 로 decode
        ObjectMapper objectMapper = new ObjectMapper();
        UsersDTO usersDTO;
        try {
            usersDTO = objectMapper.readValue(signupDataJson, UsersDTO.class);
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
            usersDTO.setProfilePic(backUrl + "/images/user/profilePic/" + savedName);

            try {
                profileImg.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("프로필 이미지 업로드 실패");
            }
        }

        Object result = authService.signup(usersDTO);

        if(result instanceof Users){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(500).body("회원가입 실패 : " + result);
    }

    /**아이디 중복검사*/
    @GetMapping("checkUserId")
    public ResponseEntity checkUserId(@RequestParam Map<String, Object> parameters){
        String userId = (String) parameters.get("userId");
        Map<String, String> response = new HashMap<>();
        // 유효성 검사
        if(!EmailValidator.isValidEmail(userId)){
            response.put("message", "유효하지않은 형식입니다. \n다시 입력해주세요.");
        }
        // 중복 여부
        else if(authService.isUserIdExists(userId)){
            response.put("message", "중복된 아이디 입니다. \n다시 입력해주세요.");
        }
        else {
            response.put("message", "사용 가능한 아이디입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**닉네임 중복검사*/
    @GetMapping("checkNickname")
    public ResponseEntity checkName(@RequestParam("nickname") String nickname){
        Map<String, String> response = new HashMap<>();
        // 유효성 검사
        if(!NameValidator.isValidName(nickname)){
            response.put("message", "유효하지않은 형식입니다. \n다시 입력해주세요.");
        }
        // 중복 여부
        else if(authService.isUserNameExists(nickname)){
            response.put("message", "중복된 닉네임 입니다. \n다시 입력해주세요.");
        }
        else {
            response.put("message", "사용 가능한 닉네임입니다.");
        }

        return ResponseEntity.ok(response);
    }

    /**access 토큰 재발급*/
    @PostMapping("refreshAccessToken")
    public ResponseEntity refreshAccessToken(HttpServletRequest request){
        String header = request.getHeader(AuthConstants.REFRESH_TOKEN_HEADER);

        try {
            String refreshTokenFromClient = TokenUtils.splitHeader(header);
            // 토큰 유효성 검사(만료여부 조작여부 등)
            if(TokenUtils.isValidToken(refreshTokenFromClient)){
                Claims claims = TokenUtils.getClaimsFromToken(refreshTokenFromClient);
                Optional<Users> user = authService.findUser(claims.get("userId").toString());
                // DB의 Refresh 토큰과 일치 여부
                if(user.isPresent()){
                    Users foundUserFromDB = user.get();
                    // 일치하면 access 토큰 재발급
                    if(foundUserFromDB.getRefreshToken() == refreshTokenFromClient){
                        String accessToken = TokenUtils.generateAccessToken(foundUserFromDB);
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.set(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
                        return ResponseEntity.ok()
                                .headers(responseHeaders)
                                .body("Access 토큰 재발급 성공");
                    }else {
                        throw new RuntimeException("토큰이 일치 하지 않습니다.");
                    }
                } else {
                    throw new RuntimeException("토큰 정보에 일치하는 회원이 없습니다.");
                }
            } else {
                throw new RuntimeException("Refresh 토큰 만료");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)   //401
                    .body(e.getMessage());
        }
    }

}
