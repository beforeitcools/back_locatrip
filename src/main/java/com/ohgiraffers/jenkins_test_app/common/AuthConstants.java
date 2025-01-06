package com.ohgiraffers.jenkins_test_app.common;

public class AuthConstants {

    // HTTP 요청의 헤더에 인증 정보를 포함하기 위해 사용하는 키
    //  인증에 필요한 헤더와 토큰 타입을 일관성 있게 관리하기 위해서 사용됨.
    public static final String AUTH_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh_Token";
    public static final String TOKEN_TYPE = "BEARER"; // 토큰 값 인트로
}

// Authorization: BEARER eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...

/*Bearer는 토큰 기반 인증에서 사용되는 인증 유형 중 하나
API 요청 시 Authorization 헤더에 토큰을 포함하는 방식을 사용하며, 토큰 앞에 "Bearer "를 붙여 보내는 것이 일반적인 규칙
Bearer라는 용어는 "소지자"라는 의미로, 토큰을 가진 사용자가 인증을 받았다는 것을 의미
 토큰을 소유한 사용자는 인증이 된 것으로 간주되어 특정 리소스에 접근할 수 있음. 즉, 이 토큰을 "소지"한 사용자는 인증을 통과했다고 판단
*/
