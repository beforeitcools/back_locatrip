package com.ohgiraffers.jenkins_test_app.auth.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyAuthority('USER')")    // Spring Security에서 사용하는 애노테이션으로, 메서드가 호출되기 전에 특정 권한을 가진 사용자만 접근할 수 있도록 제어
public class AuthUserController {

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
