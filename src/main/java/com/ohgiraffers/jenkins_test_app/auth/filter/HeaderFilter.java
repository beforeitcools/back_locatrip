package com.ohgiraffers.jenkins_test_app.auth.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HeaderFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // ServletResponse를 HttpServletResponse로 캐스팅하여 응답에 헤더를 추가
        HttpServletResponse res = (HttpServletResponse) response;
        // 외부 요청에 대한 접근을 허용할지 여부를 설정
        // "*"는 모든 도메인에서의 요청을 허용한다는 의미
        res.setHeader("Access-Control-Allow-Origin", "*"); // 외부 요청 응답 허용 여부 등록
        // 요청할 수 있는 HTTP 메소드 종류를 설정
        // GET, POST, PUT, DELETE 메소드의 요청을 허용
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE"); // 외부 리소스 요청 허용 여부
        // 외부 리소스 요청에 대해 얼마나 오랫동안 응답을 캐시할지를 설정
        // 자주 사용되는 데이터를 임시 저장하여,
        // 같은 데이터를 다시 요청할 때 더 빠르게 응답을 제공할 수 있게 만드는 기술입니다. 즉, 캐싱은 반복되는 작업을 최적화하고 성능을 향상시키는 데 중요한 역할을 합니다.
        // 3600초(1시간) 동안 캐시를 허용
        res.setHeader("Access-Control-Max-Age", "3600"); // 외부 캐싱
        // X-Requested-With, Content-Type, Authorization, X-XSRF-token 헤더의 요청을 허용
        // X-Requested-With: 클라이언트가 AJAX 요청을 보낼 때 사용하는 헤더
        // Content-Type: 요청 본문에 담기는 데이터의 타입을 명시
        // Authorization: 서버에 요청할 때, 인증 정보를 포함하는 헤더
        // X-XSRF-token: 보안상의 이유
        // 네, 만약 토큰 기반 인증을 사용하고 있다면 Access-Control-Allow-Headers에 토큰을 포함하는 헤더가 명시되어야 합니다.
        // 예를 들어, Authorization 헤더를 통해 토큰을 전달하는 경우,
        // 서버가 해당 헤더를 허용하도록 설정해야 클라이언트에서 토큰을 포함한 요청을 성공적으로 보낼 수 있습니다.
        res.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, X-XSRF-token");  // 어떤 헤더 요청을 허용 할지
        // 서버가 인증 정보를 포함하지 않도록 설정
        // 이 설정이 false로 되어 있으면 쿠키는 자동으로 포함되지 않으며, 브라우저가 자동으로 쿠키를 포함한 요청을 보내지 않게 됩니다.
        // 보안상의 이유로 직접 헤더에 포함시키는 것 말고는 제한함.
        res.setHeader("Access-Control-Allow-Credentials", "false"); //서버가 인증 정보를 포함하지 않도록 설정
        chain.doFilter(request,response);


    }
}
