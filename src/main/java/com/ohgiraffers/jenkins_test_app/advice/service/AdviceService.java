package com.ohgiraffers.jenkins_test_app.advice.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.jenkins_test_app.advice.dto.*;
import com.ohgiraffers.jenkins_test_app.advice.entity.LocalAdvice;
import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.advice.repository.AdviceRepository;
import com.ohgiraffers.jenkins_test_app.advice.repository.PostRepository;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyPostRepository;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
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

    @Autowired
    TripRepository tripRepository;
    @Autowired
    private AdviceRepository adviceRepository;
    @Autowired
    private PostRepository postRepository;

    public List<PostsWithMyLocalAreaDTO> getPostWithMyLocalArea(String localArea) {
        List<Object[]> postsWithMyLocalArea = myPostRepository.getPostWithMyLocalArea(localArea);
        List<PostsWithMyLocalAreaDTO> postsWithMyLocalAreaDTOList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (Object[] o : postsWithMyLocalArea) {
            PostsWithMyLocalAreaDTO dto = new PostsWithMyLocalAreaDTO();
            dto.setPostId((Integer) o[0]);
            dto.setTitle((String) o[1]);
            // selected regions list Logic 다시 생각 필요
            /*try {
                Map<String, Object> data = objectMapper.readValue((String) o[2], Map.class);
                dto.setSelectedRegion((String) ((List) data.get("selectedRegions")).get(0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
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
                Object obj = o[7];
                Object parsed = objectMapper.readValue((String) obj, Object.class);
                Map<String, Object> data;
                if(parsed instanceof List){
                    List<Map<String, Object>> dataList = objectMapper.readValue((String) o[7], new TypeReference<List<Map<String, Object>>>() {});
                    data = dataList.get(0);
                    System.out.println("list data: " + data);
                } else {
                    data = objectMapper.readValue((String) o[7], Map.class);
                    System.out.println("map data: " + data);
                }
                Map<String, Object> tripData = (Map<String, Object>) data.get("trip");
                System.out.println(tripData);
                String startDateString = (String) tripData.get("startDate");
                System.out.println(startDateString);
                LocalDate startDate = LocalDate.parse(startDateString);
                System.out.println("11111");
                String endDateString = (String) tripData.get("endDate");
                System.out.println("11111");
                LocalDate endDate = LocalDate.parse(endDateString);
                System.out.println("11111");

                dto.setStartDate(startDate);
                dto.setEndDate(endDate);
                System.out.println("11111");
                List<Map<String, Object>> selectedRegions = (List<Map<String, Object>>) tripData.get("selectedRegions");
                List<String> selectedRegionsList = new ArrayList<>();
                for (Map<String, Object> selectedRegion : selectedRegions) {
                    selectedRegionsList.add((String) selectedRegion.get("region"));
                }
                dto.setSelectedRegionsList(selectedRegionsList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("11111");
            dto.setAdviceCount((Long) o[8]);
            System.out.println(dto);
            postDataDTOList.add(dto);
        }
        System.out.println("전체 service: " + postDataDTOList);
        return postDataDTOList;
    }

    public List<ValidTripForPostDTO> getValidTrips(Integer userId) {
        return tripRepository.getValidTripsWithMoreThanThreeLocations(userId);
    }

    public String saveAdvice(LocalAdvice localAdvice) {
        localAdvice.setIsSelected(0);
        localAdvice.setStatus(1);

        if(adviceRepository.save(localAdvice) instanceof LocalAdvice){
            return "첨삭등록 성공";
        }else{
            return "첨삭등록 실패";
        }
    }

    public Map<String, Object> getAdviceData(PostIdAndLocattionIdDTO postIdAndLocattionIdDTO) {
        Map<String, Object> adviceData = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        if(postIdAndLocattionIdDTO.getLocationId() != 0){
            Posts foundPost = postRepository.findById(postIdAndLocattionIdDTO.getPostId()).get();
            try {
                List<Map<String, Object>> dataList = objectMapper.readValue((String) foundPost.getAdvicedTripData(), new TypeReference<List<Map<String, Object>>>() {});
                Map<String, Object> data = dataList.get(0);
                Map<String, Object> locationData = (Map<String, Object>) data.get("location");
                adviceData.put("locationName", locationData.get("name"));
                adviceData.put("locationAddress", locationData.get("address"));
                adviceData.put("locationCategory", locationData.get("category"));
                for(Map<String, Object> tripDayLocation : dataList){
                    if(tripDayLocation.get("id") == postIdAndLocattionIdDTO.getLocationId()){
                        adviceData.put("orderIndex", tripDayLocation.get("orderIndex"));
                        break;
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        List<AdvicesWithUserInfoDTO> adviceList = adviceRepository.getAdvicesOnLocation(postIdAndLocattionIdDTO.getLocationId());
        adviceData.put("adviceList", adviceList);
        return adviceData;
    }


    public List<LocalAdvice> selectAdviceList(Integer postId, Integer userId) {
        System.out.println("Service 호출: postId = " + postId + ", userId = " + userId);
        try {
            List<LocalAdvice> adviceList = adviceRepository.findByPostIdAndUserId(postId, userId);
            if(adviceList.isEmpty()){
                return null;
            }else {
                return adviceList;
            }

        } catch (Exception e) {
            System.err.println("Repository 호출 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
