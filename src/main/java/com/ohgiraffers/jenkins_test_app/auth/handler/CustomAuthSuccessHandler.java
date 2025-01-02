package com.ohgiraffers.jenkins_test_app.auth.handler;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.model.DetailsUser;
import com.ohgiraffers.jenkins_test_app.common.AuthConstants;
import com.ohgiraffers.jenkins_test_app.common.utils.ConvertUtil;
import com.ohgiraffers.jenkins_test_app.common.utils.TokenUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Configuration
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    // 요청 성공 시 실행 핸들러를 가로챔

    @Override                          // 현재 HTTP 요청 객체                                     //인증 정보 객체
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 인증된 사용자 정보를 가져옵니다. authentication.getPrincipal()은 인증된 사용자 정보를 반환.
        // 이 객체를 `DetailsUser`로 캐스팅하고, 그 안의 `User` 객체를 가져옵니다.
        Users user = ((DetailsUser) authentication.getPrincipal()).getUser();

        // `User` 객체를 JSON 형식으로 변환합니다.
        // ConvertUtil은 `User` 객체를 JSON으로 변환
        JSONObject jsonValue = (JSONObject) ConvertUtil.convertObjectToJsonObject(user);
        // json 형식으로 변환 후 토큰 생성을 위함
        HashMap<String,Object> responseMap = new HashMap<>();
        JSONObject jsonObject;
        // 사용자 상태가 "N"인 경우 (휴면 계정 상태)
        if (user.getStatus() == 0){
            // 휴면 계정인 경우 사용자 정보와 메시지를 응답에 담습니다.
            responseMap.put("userInfo",jsonValue);
            responseMap.put("message","탈퇴한 계정입니다.");
        }else {
            // 정상 계정인 경우 JWT 토큰을 생성하고 응답에 포함시킵니다.
            String accessToken = TokenUtils.generateAccessToken(user);  // 생성된 토큰 조작부
            String refreshToken = TokenUtils.generateRefreshToken(user);
            // 정상 로그인 시 사용자 정보와 성공 메시지, 생성된 토큰을 응답에 담습니다.
            responseMap.put("userInfo",jsonValue);
            responseMap.put("message","로그인 성공");
            // 생성된 토큰을 HTTP 응답 헤더에 추가합니다.
            // AuthConstants.AUTH_HEADER: 헤더 이름 (예: Authorization)
            // AuthConstants.TOKEN_TYPE: "Bearer"와 같은 토큰 유형
            // 최종적으로 Authorization 헤더로 JWT 토큰을 클라이언트에 전달
            // Authorization 헤더에 JWT 토큰을 포함하여 클라이언트에게 전달하는 것
            response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + accessToken);
            response.addHeader(AuthConstants.REFRESH_TOKEN_HEADER, AuthConstants.TOKEN_TYPE + " " + refreshToken);
        }

        // 응답에 보낼 데이터(사용자 정보와 메시지)를 JSON 객체로 변환합니다.
        jsonObject = new JSONObject(responseMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 클라이언트로 응답을 보내기 위한 PrintWriter 객체를 가져옵니다.
        PrintWriter printWriter = response.getWriter();
        // 응답 본문에 JSON 객체를 출력합니다.
        printWriter.println(jsonObject);
        // 데이터를 출력한 후 스트림을 플러시하여 실제로 클라이언트로 전송합니다. 본문(Body) 데이터
        printWriter.flush();  // 내보냄
        printWriter.close();
    }
}
