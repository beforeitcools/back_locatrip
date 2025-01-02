package com.ohgiraffers.jenkins_test_app.auth.model;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetailsUser implements UserDetails {

    //    DetailsUser 클래스는 UserDetails 인터페이스를 구현하여
    //    Spring Security의 인증을 위해 사용자 정보를 정의하는 역할을 합니다.
    //    Spring Security는 인증 절차에서 UserDetails 객체를 사용하여 사용자의 로그인 상태와 권한을 확인하고,
    //    이 클래스는 User 엔티티의 데이터를 UserDetails 형식으로 변환하여 Spring Security와 연동할 수 있게 해줍니다.

    // DB에서 가져온 사용자 정보를 이 클래스의 형식에 맞춰 Spring Security로 전달하기 위한 사용자 객체
    private Users user;

    public DetailsUser() {
    }
    // 사용자 정보를 포함하는 Optional<Users>를 매개변수로 받아, 내부 user 필드에 저장하는 생성자
    public DetailsUser(Optional<Users> user) {
        this.user = user.get(); // 빈값이면 에러가 나는데 방지하기 위해서 optional 객체로 반환해줌
    }                  // Optional의 get() 메서드를 사용해 User 객체를 추출. 빈 값일 경우 에러 방지용 Optional 사용

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // Spring Security는 이 메서드를 통해 사용자가 가진 권한(예: ROLE_USER, ROLE_ADMIN 등)을 확인하고, 이 권한에 따라 접근 가능한 리소스나 기능을 제어합니다.
    @Override   // 권한설정 --- 중요 // 사용자 권한 목록을 반환하는 메서드로, 각 권한은 GrantedAuthority 인터페이스의 익명 구현으로 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoleList()) {
            authorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role;
                }
            });
        }

        return authorities;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }


    // 여기서 아래 내용을 관리하면 DB에 다 들어가 있어야함.  필수요소 X



    @Override
    public boolean isAccountNonExpired() {
        /*
         * 계정 만료 여부 메소드 false이면 사용할 수 없음
         * */
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        /*
         * 계정이 잠겨있는지 확인하는 메서드 false이면 해당 계정을 사용할 수 없음
         * 반복실패 lock 등의 로직은 여기서 짜 줘야함.
         * */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 탈퇴 여부 표현 메소드 false면 사용 X
        if(user.getStatus() == 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean isEnabled() {
        //계정 비활성화 여부 false면 사용 X
        return true;
    }
}
