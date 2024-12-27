package com.ohgiraffers.jenkins_test_app.auth.handler;

import com.ohgiraffers.jenkins_test_app.auth.model.DetailsUser;
import com.ohgiraffers.jenkins_test_app.auth.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    // CustomAuthenticationProvider는 Spring Security에서 인증을 담당하는 커스텀 클래스

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // 조회 해온 정보와 토큰 값을 비교하는 메서드
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 로그인 요청에서 전달된 인증 토큰을 가져옴 (사용자명과 비밀번호)
        // CustomAuthenticationFilter 에서 오버라이드해서 구현
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = loginToken.getName();
        String password = (String) loginToken.getCredentials();  // 토큰이 가지고 있는 값

        // 2. DB에서 username 에 해당하는 정보 조회
        // DB에 정보 없으면(없는 userId로 조회가 안될경우
        DetailsUser foundUser = (DetailsUser) detailsService.loadUserByUsername(username);

        // 사용자가 입력한 username,password 와 아이디의 비밀번호를 비교하는 로직을 수행함
        if(!bCryptPasswordEncoder.matches(password, foundUser.getPassword())){  // 입력값과 db값 비교 (암호화 된 값)
            throw new BadCredentialsException("password가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(foundUser,password, foundUser.getAuthorities());
        //아이디,비밀번호,권한목록 (임시토큰 발행)
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /*supports() 메서드는 이 AuthenticationProvider가 어떤 타입의 인증을 처리할 수 있는지 확인합니다.
          여기서는 UsernamePasswordAuthenticationToken만 처리할 수 있도록 설정되어 있습니다.*/
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
