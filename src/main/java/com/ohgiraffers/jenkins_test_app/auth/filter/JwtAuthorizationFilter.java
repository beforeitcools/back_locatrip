package com.ohgiraffers.jenkins_test_app.auth.filter;

import com.ohgiraffers.jenkins_test_app.auth.Enum.UserRole;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.model.DetailsUser;
import com.ohgiraffers.jenkins_test_app.common.AuthConstants;
import com.ohgiraffers.jenkins_test_app.common.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 권한이 필요 없는 URL 리스트
        List<String> roleLeessList = Arrays.asList(
                "/auth/signup", "/auth/login", "/auth/checkUserId", "/auth/checkNickname", "/auth/logout"
        );

        // 요청된 URI가 권한이 필요없는 목록에 포함되어 있으면, 필터를 통과시킴
        if(roleLeessList.contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;  // 체이닝 걸려있어서 / 체이닝 하면 return 으로 끊어주기만 하면 알아서 받아감
        }

        // 권한이 필요하면 여기부터 실행
        String header = request.getHeader(AuthConstants.AUTH_HEADER); // 요청 헤더에서 AUTH_HEADER 값을 가져옴

        try {
            // 만약 헤더가 존재하고, 비어 있지 않다면
            if(header != null && !header.equalsIgnoreCase("")){
                // 헤더에서 "Bearer"와 함께 전달된 토큰을 분리
                String token = TokenUtils.splitHeader(header);

                // 토큰이 유효한지 체크
                if(TokenUtils.isValidToken(token)){
                    // 트루로 넘어오면 토큰 정상 - 만료되지 않음.
                    // 토큰이 유효하면, 토큰의 클레임 정보를 가져옴
                    Claims claims = TokenUtils.getClaimsFromToken(token);
                    // 인증 정보 객체 생성 (DetailsUser는 인증 정보를 담는 사용자 정의 클래스)
                    DetailsUser authentication = new DetailsUser();  // AbstractAuthenticationToken 에 들어갈 타입때문에 지정
                    // 사용자 정보를 User 객체에 설정
                    Users user = new Users();
                    // 클레임에서 userId 값을 가져옴
                    user.setUserId(claims.get("userId").toString());
                    // 클레임에서 역할(Role)을 가져와 Role Enum으로 변환
                    user.setRole(UserRole.valueOf(claims.get("Role").toString())); // 들어간 값 대로 권한 반환
                    // DetailsUser 객체에 사용자 정보 저장
                    authentication.setUser(user); // AbstractAuthenticationToken 에 넣기 위해 넣어줌 디테일객체에 user 정보 담아줌
                    // 디테일 객체에 담기 위해 user 엔티티 생성후 담아줌.. 직접 담을수없음   담고 아래 토큰에 넣어줌

                    // UsernamePasswordAuthenticationToken 생성
                    AbstractAuthenticationToken authenticationToken =                               // getAuthorities()는 사용자에게 부여된 **권한(roles)**을 반환
                            UsernamePasswordAuthenticationToken.authenticated(authentication, token, authentication.getAuthorities());
                    // 인증 정보를 요청에 포함시켜서 SecurityContextHolder에 설정
                    // SecurityContextHolder에 인증 정보 설정
                    //인증된 토큰 정보를 SecurityContextHolder에 저장하여, 이후 다른 보안 필터나 서비스에서 인증 정보를 활용할 수 있도록 합니다.
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    // SecurityContextHolder에 인증 정보를 설정하여, 이후에 권한 체크를 자동으로 처리하도록 함
                    // SecurityContextHolder에 사용자의 인증 정보를 담아두면, 이후 요청에서 인증된 사용자로서 애플리케이션의 보안 설정을 처리할 수 있습니다.
                    // 이렇게 인증 정보를 설정하면, 애플리케이션에서 다른 기능을 사용할 수 있는 중요한 보안 컨텍스트가 확보됩니다.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request,response);  // 체이닝 걸려있어서 알아서 받아감
                }else {
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }

            }else {
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }
        }catch (Exception e){
            // 예외가 발생하면 JSON 형식으로 응답을 반환
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonresponseWrapper(e);
            printWriter.println(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }

    // 예외에 맞는 메시지를 반환하는 메서드
    private JSONObject jsonresponseWrapper(Exception e){
        String resultMsg = "";
        if(e instanceof ExpiredJwtException){
            resultMsg = "Token Expired";  // 토큰 만료
        } else if (e instanceof SignatureException) {
            resultMsg = "Token SignatureException login";  // 서명 오류
        } else if (e instanceof JwtException) {
            resultMsg = "Token parsing JwtException";  // 토큰 파싱 오류
        }else {
            resultMsg = "Other token Error"; // 기타 오류
        }
        // JSON 응답을 작성하기 위해 Map을 생성
        HashMap<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("status",401); // HTTP 상태 코드 401 (인증 오류)
        jsonMap.put("message", resultMsg);
        jsonMap.put("reason",e.getMessage());
        JSONObject jsonObject = new JSONObject(jsonMap);
        return jsonObject;
    }
}
