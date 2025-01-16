package com.ohgiraffers.jenkins_test_app.advice.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AdviceService {

    @Autowired
    UserRepository userRepository;


}
