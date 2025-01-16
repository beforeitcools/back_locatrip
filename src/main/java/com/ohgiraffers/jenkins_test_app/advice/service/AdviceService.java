package com.ohgiraffers.jenkins_test_app.advice.service;

import com.ohgiraffers.jenkins_test_app.advice.dto.PostDataDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostsWithMyLocalAreaDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdviceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyPostRepository myPostRepository;

    public List<PostsWithMyLocalAreaDTO> getPostWithMyLocalArea(String localArea) {
        List<Object[]> postsWithMyLocalArea = myPostRepository.getPostWithMyLocalArea(localArea);
        List<PostsWithMyLocalAreaDTO> postsWithMyLocalAreaDTOList = new ArrayList<>();
        for (Object[] o : postsWithMyLocalArea) {
            PostsWithMyLocalAreaDTO dto = new PostsWithMyLocalAreaDTO();
            dto.setPostId((Integer) o[0]);
            dto.setTitle((String) o[1]);
            dto.setSelectedRegionsList(List.of(((String) o[2]).split(",")));
            postsWithMyLocalAreaDTOList.add(dto);
        }
        return postsWithMyLocalAreaDTOList;
    }

    public List<PostDataDTO> getAllPostData() {
        List<Object[]> postList = myPostRepository.getAllPostData();
        List<PostDataDTO> postDataDTOList = new ArrayList<>();
        for (Object[] o : postList) {
            PostDataDTO dto = new PostDataDTO();
            dto.setPostId((Integer) o[0]);
            dto.setTitle((String) o[1]);
            dto.setContent((String)o[2]);
            dto.setCreatedAt((LocalDateTime) o[3]);
            dto.setUserId((Integer) o[4]);
            dto.setNickname((String) o[5]);
            dto.setProfilePic((String) o[6]);
            dto.setStartDate((LocalDate) o[7]);
            dto.setEndDate((LocalDate) o[8]);
            dto.setSelectedRegionsList(List.of(((String) o[9]).split(",")));
            dto.setAdviceCount((Integer) o[10]);
            postDataDTOList.add(dto);
        }
        return postDataDTOList;
    }
}
