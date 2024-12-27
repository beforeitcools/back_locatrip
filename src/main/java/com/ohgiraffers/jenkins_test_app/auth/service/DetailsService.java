package com.ohgiraffers.jenkins_test_app.auth.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.model.DetailsUser;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


// UserDetailsService는 Spring Security에서 사용자 인증을 위해 필요한 인터페이스로, 사용자 정보를 로드하는 loadUserByUsername 메서드를 구현해야 합니다.
@Service
public class DetailsService implements UserDetailsService {

    private final AuthService authService;

    public DetailsService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서 username은 구현된 메소드 이름이기 때문에 DetailsUser 클래스의 오버라이드된 getter 에 변경해주면된다.(ID로 locat에서는 userId)

        // 유효성 검사 진행
        if(username == null || username.equals("")){
            throw new AuthenticationServiceException(username+" is Empty");
        }

        // db에서 username에 해당하는 정보를 꺼내온다.
        Optional<Users> user = authService.findUser(username);
        // 사용자가 존재하는 경우 DetailsUser 객체를 반환
        if (user.isPresent()) {
            return new DetailsUser(user);
        } else {
            // 사용자가 존재하지 않는 경우 예외를 발생시킴
            throw new AuthenticationServiceException(username);
        }
    }
}
