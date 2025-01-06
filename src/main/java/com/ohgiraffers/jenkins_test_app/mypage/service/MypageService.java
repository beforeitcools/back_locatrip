package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageService {

    @Autowired
    MypageRepository mypageRepository;
}
