package com.ohgiraffers.jenkins_test_app.main.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MainService {


    @Autowired
    UserRepository userRepository;

    public Users getUserInfo(Integer hostId) {

        if(hostId == null){
            return null;
        }


        Optional<Users> usersOptional = userRepository.findById(hostId);

        return usersOptional.orElse(null);

    }
}
