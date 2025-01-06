package com.ohgiraffers.jenkins_test_app.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    // 지정된 url 요청시 해당 요청을 가로채서 검증 로직을 수행하는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // UsernamePasswordAuthenticationToken 객체를 선언. 이 객체는 사용자가 입력한 사용자명과 비밀번호를 담는 토큰
        UsernamePasswordAuthenticationToken authenticationToken;

        try {
            // getAuthRequest(request) 메서드를 호출하여 요청(request)에서
            // 사용자 인증에 필요한 정보(예: 사용자명, 비밀번호)를 추출해 토큰 객체로 생성
            authenticationToken = getAuthRequest(request);
            // 사용자 인증에 필요한 정보(사용자명, 비밀번호 등)를 담은 토큰에 추가적인 세부 정보를 설정 (예: 요청의 상세 정보)
            // setDetails()는 인증 요청에 대한 추가 정보를 설정하는 메서드
            setDetails(request, authenticationToken); //사용자가 입력한 정보를 담은 토큰 정보
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // AuthenticationManager를 통해 인증을 시도. 생성한 authenticationToken을 매니저에 전달하여 실제 인증 처리
        return this.getAuthenticationManager().authenticate(authenticationToken);
        // 인증이 성공하면 인증된 정보를 포함한 Authentication 객체가 반환됨시 토큰을 매니저에 담아 보내줌
    }


    /**
     *  사용자의 로그인 리소스 요청시 요청 정보를 임시 토큰에 저장하는 메소드
     *
     * @Param request = httpServletRequest
     * @return UserPasswordAuthenticationToken
     * @throw IOException e
     * */
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {
        // Jackson ObjectMapper 인스턴스를 생성하여 JSON을 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 파싱 후 리소스를 자동으로 닫도록 설정 (성능 최적화 및 자원 관리)
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        // HTTP 요청의 본문에서 JSON 데이터를 추출하여 UsersDTO 객체로 매핑
        // request.getInputStream()은 요청 본문을 입력 스트림으로 가져오며, 이를 objectMapper가 UsersDTO 객체로 변환
        UsersDTO user = objectMapper.readValue(request.getInputStream(), UsersDTO.class);
        //json 데이터를 뿌려줌. dto 형식으로 매핑  기본 설정이 세터. 생성자 만드는 건 자유
        // 위의 코드에서 파싱된 UsersDTO 객체를 통해 사용자 ID와 비밀번호를 가져와서 UsernamePasswordAuthenticationToken 생성
        // 생성된 토큰은 사용자 ID(user.getUserId())와 비밀번호(user.getPassword())를 포함하고, 이를 인증에 사용할 예정
        return new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword());  //임시 토큰 발행
    }
}
/*임시 인증 토큰: 이 토큰은 AuthenticationManager에서
사용자를 인증하는 데 필요한 정보(사용자 ID와 비밀번호)를 담고 있는 객체입니다.
인증이 완료되면 이 토큰은 인증된 Authentication 객체로 교체됩니다.*/