package com.ohgiraffers.jenkins_test_app.auth.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.auth.common.EmailValidator;
import com.ohgiraffers.jenkins_test_app.auth.common.NameValidator;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.service.AuthService;
import com.ohgiraffers.jenkins_test_app.common.AuthConstants;
import com.ohgiraffers.jenkins_test_app.common.ServerUrlConstants;
import com.ohgiraffers.jenkins_test_app.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/auth/*")
public class AuthController {


    @Autowired
    private AuthService authService;

    /**회원가입*/
    @PostMapping("signup")
    public ResponseEntity signup(@RequestPart("signupData") String signupDataJson,
                                 @RequestPart(value = "profileImg", required = false) MultipartFile profileImg){
        // JsonEncode 되어 있는 signupData를 SignupDTO 로 decode
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        UsersDTO usersDTO;
        byte[] bytes = signupDataJson.getBytes(StandardCharsets.ISO_8859_1);
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
        System.out.println("AT 발행 시작~~~~~~1111");
        String header = request.getHeader(AuthConstants.REFRESH_TOKEN_HEADER);
        System.out.println("header 는 있니");

        try {
            String refreshTokenFromClient = TokenUtils.splitHeader(header);
            // 토큰 유효성 검사(만료여부 조작여부 등)
            if(TokenUtils.isValidToken(refreshTokenFromClient)){
                System.out.println("AT 발행 시작~~~~~~2222");
                Claims claims = TokenUtils.getClaimsFromToken(refreshTokenFromClient);
                Optional<Users> user = authService.findUser(claims.get("userId").toString());
                // DB의 Refresh 토큰과 일치 여부
                if(user.isPresent()){
                    System.out.println("AT 로 user 찾아옴");
                    Users foundUserFromDB = user.get();
                    // 일치하면 access 토큰 재발급
                    if(foundUserFromDB.getRefreshToken().equals(refreshTokenFromClient)){
                        String accessToken = TokenUtils.generateAccessToken(foundUserFromDB);
                        System.out.println("AT 발행 성공~~~~~~");
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
            System.out.println("403 보내나??");
            return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN)   //403
                    .body(e.getMessage());
        }
    }

    /**로그아웃*/
    @PostMapping("logout")
    public ResponseEntity logout(HttpServletRequest request){
        Map<String, String> response = new HashMap<>();
        System.out.println("로그아웃 시작");
        String header = request.getHeader(AuthConstants.REFRESH_TOKEN_HEADER);
        System.out.println(header);

        try {
            String refreshTokenFromClient = TokenUtils.splitHeader(header);
            System.out.println(refreshTokenFromClient);
            // 토큰 유효성 검사(조작여부 등)
            if(TokenUtils.isValidToken(refreshTokenFromClient)) {
                Claims claims = TokenUtils.getClaimsFromToken(refreshTokenFromClient);
                System.out.println(claims);
                Optional<Users> user = authService.findUser(claims.get("userId").toString());
                // DB의 Refresh 토큰과 일치 여부
                if (user.isPresent()) {
                    Users foundUserFromDB = user.get();
                    System.out.println(foundUserFromDB);
                    System.out.println(foundUserFromDB.getRefreshToken());
                    System.out.println(refreshTokenFromClient);
                    // 일치하면 DB에서 Refresh 토큰 제거
                    if (foundUserFromDB.getRefreshToken().equals(refreshTokenFromClient)) {
                        System.out.println("여는 기오니??????");
                        if (authService.deleteRefreshTokenInDB(foundUserFromDB)) {
                            System.out.println("DB의 refresh 토큰 제거 성공");
                            response.put("message", "로그아웃 완료");

                            return ResponseEntity.ok(response);
                        } else {
                            System.out.println("DB의 refresh 토큰 제거 실패");
                            response.put("message", "DB의 refresh 토큰 제거 실패");
                            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                                    .body(response);
                        }
                    } else {
                        response.put("message", "토큰이 일치 하지 않습니다.");
                        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                                .body(response);
                    }
                } else {
                    response.put("message", "존재하지 않는 회원 정보 입니다.");
                    return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                            .body(response);
                }
            }else {
                response.put("message", "토큰 만료");
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)   //401
                        .body(response);
            }
        } catch (Exception e){
            response.put("message", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류 발생");
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)   //401
                    .body(response);
        }
    }

}
