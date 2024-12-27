package com.ohgiraffers.jenkins_test_app.auth.interceptor;

import com.ohgiraffers.jenkins_test_app.common.AuthConstants;
import com.ohgiraffers.jenkins_test_app.common.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.rmi.RemoteException;

public class JwtTokenInterceptor implements HandlerInterceptor {

    // , 요청이 처리되기 전에 JWT 토큰을 검사하는 역할
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 요청 헤더에서 "Authorization" 헤더 값을 가져옵니다.
        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        // "Authorization" 헤더에서 토큰을 추출합니다. 예: "Bearer <token>"
        String token = TokenUtils.splitHeader(header);
        // 토큰이 존재하는지 확인
        if (token != null){
            //유효성체크
            if(TokenUtils.isValidToken(token)){
                return true;
            }else {
                throw new RemoteException("token 만료");
            }
        }else {
            throw new RemoteException("token 정보가 없습니다.");
        }

    }
}
