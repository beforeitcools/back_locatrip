package com.ohgiraffers.jenkins_test_app.common.utils;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.model.DetailsUser;
import com.ohgiraffers.jenkins_test_app.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    @Autowired
    private final AuthService authService;

    private SecurityUtil(){
        this.authService = new AuthService();
    }

    public Users getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof DetailsUser) {
            String userId = ((DetailsUser) authentication.getPrincipal()).getUser().getUserId();
            Optional<Users> user = authService.findUser(userId);
            if(user.isPresent()) {
                Users foundUser = user.get();
                return foundUser;
            } else {
                throw new RuntimeException("인증된 회원이 존재하지 않습니다.");
            }
        } else {
            throw new RuntimeException("인증된 회원이 존재하지 않습니다.");
        }
    }
}
