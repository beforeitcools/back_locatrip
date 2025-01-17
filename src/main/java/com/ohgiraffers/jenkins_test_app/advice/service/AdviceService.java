package com.ohgiraffers.jenkins_test_app.advice.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostDataDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostsWithMyLocalAreaDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object[] o : postsWithMyLocalArea) {
            PostsWithMyLocalAreaDTO dto = new PostsWithMyLocalAreaDTO();
            dto.setPostId((Integer) o[0]);
            dto.setTitle((String) o[1]);
            try {
                Map<String, Object> data = objectMapper.readValue((String) o[2], Map.class);
                dto.setSelectedRegion((String) ((List) data.get("selectedRegions")).get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            postsWithMyLocalAreaDTOList.add(dto);
            System.out.println("지역 service: " + dto);
        }
        System.out.println("지역 service: " + postsWithMyLocalArea);
        return postsWithMyLocalAreaDTOList;
    }

    public List<PostDataDTO> getAllPostData() {
        List<Object[]> postList = myPostRepository.getAllPostData();
        List<PostDataDTO> postDataDTOList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object[] o : postList) {
            PostDataDTO dto = new PostDataDTO();
            dto.setPostId((Integer) o[0]);
            dto.setTitle((String) o[1]);
            dto.setContent((String)o[2]);
            dto.setCreatedAt(((Timestamp) o[3]).toLocalDateTime());
            dto.setUserId((Integer) o[4]);
            dto.setNickname((String) o[5]);
            dto.setProfilePic((String) o[6]);
            try {
                Map<String, Object> data = objectMapper.readValue((String) o[7], Map.class);
                String startDateString = (String) data.get("startDate");
                LocalDate startDate = LocalDate.parse(startDateString);
                String endDateString = (String) data.get("endDate");
                LocalDate endDate = LocalDate.parse(endDateString);

                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                dto.setSelectedRegionsList((List) data.get("selectedRegions"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dto.setAdviceCount((Long) o[8]);
            System.out.println(dto);
            postDataDTOList.add(dto);
        }
        System.out.println("전체 service: " + postDataDTOList);
        return postDataDTOList;
    }
}
