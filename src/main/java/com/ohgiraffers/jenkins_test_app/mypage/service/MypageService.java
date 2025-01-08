package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageService {

    @Autowired
    MypageRepository mypageRepository;

    @Autowired
    UserRepository userRepository;

    public Object updateProfile(UsersDTO usersDTO, Users authenticatedUser) {

        authenticatedUser.setNickname(usersDTO.getNickname());
        if(usersDTO.getProfilePic() != null && !usersDTO.getProfilePic().isEmpty()){
            authenticatedUser.setProfilePic(usersDTO.getProfilePic());
        }

        Users result = userRepository.save(authenticatedUser);
        return result;
    }


}
