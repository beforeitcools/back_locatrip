package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.advice.entity.PostFavorite;
import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.PostFavoriteDTO;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyFavoritePostRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyPostRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyTripRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MypageService {

    @Autowired
    MypageRepository mypageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyTripRepository myTripRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    MyPostRepository myPostRepository;

    @Autowired
    MyFavoritePostRepository myFavoritePostRepository;


    @Transactional
    public Object updateProfile(UsersDTO usersDTO, Users authenticatedUser) {

        authenticatedUser.setNickname(usersDTO.getNickname());
        if(usersDTO.getProfilePic() != null && !usersDTO.getProfilePic().isEmpty()){
            authenticatedUser.setProfilePic(usersDTO.getProfilePic());
        }

        Users result = userRepository.save(authenticatedUser);
        return result;
    }


    public Long getSelectedAdviceCount(Integer userId) {

        return mypageRepository.countSelectedAdvicesByUser(userId);
    }

    public List<MyTripSummaryDTO> getMyTrips(Integer userId) {

        List<MyTripSummaryDTO> myTripsOwnedByMe = myTripRepository.findTripsOwnedByUser(userId);
        List<MyTripSummaryDTO> myTripsWhereImMember = myTripRepository.findTripsWhereImMember(userId);

        List<MyTripSummaryDTO> allMyTrips = new ArrayList<>(myTripsOwnedByMe);
        allMyTrips.addAll(myTripsWhereImMember);

        System.out.println("service layer: " + myTripsOwnedByMe);
        System.out.println("service layer: " + myTripsWhereImMember);
        System.out.println("service layer: " + allMyTrips);

        return allMyTrips;
    }

    @Transactional
    public String deleteTrip(Integer tripId) {
        Trip tripToDelete = myTripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("삭제할 여행이 없습니다."));

        tripToDelete.setStatus(0);
        Trip deletedTrip = myTripRepository.save(tripToDelete);
        if(!Objects.isNull(deletedTrip) && deletedTrip.getStatus().equals(0)){
            return "여행 삭제 성공";
        }
        else return "여행 삭제 실패";
    }

    public List<MyPostSummaryDTO> getMyPosts(Integer userId) {
        List<MyPostSummaryDTO> myPostList = myPostRepository.findMyPosts(userId);

        return myPostList;
    }

    public Map<String, Object> getMyFavorites(Integer userId) {
        Map<String, Object> myFavoriteData = new HashMap<>();
        myFavoriteData.put("locations", locationRepository.getMyFavoriteLocationsData(userId));
        myFavoriteData.put("posts", myFavoritePostRepository.getMyFavoritePostsData(userId));

        System.out.println("service layer mfd: " + myFavoriteData);
        System.out.println("service layer mfd locations: " + myFavoriteData.get("locations"));
        System.out.println("service layer mfd posts: " + myFavoriteData.get("posts"));

        return myFavoriteData;
    }

    @Transactional
    public Object insertFavoritePost(PostFavoriteDTO postFavoriteDTO) {

        boolean postExists = myPostRepository.existsByIdAndStatus(postFavoriteDTO.getPostId(), 1);

        // 있는 게시글인지 확인(status = 1)
        if(!postExists){
            System.out.println("없는 게시글 입니다.");
           return "없는 게시글 입니다.";
        } else {
            // 중복 검사(이미 저장되어 있는지)
            if(!myFavoritePostRepository.existsByPostIdAndUserId(postFavoriteDTO.getPostId(), postFavoriteDTO.getUserId())){
                PostFavorite postFavorite = new PostFavorite(
                        postFavoriteDTO.getPostId(), postFavoriteDTO.getUserId()
                );
                return myFavoritePostRepository.save(postFavorite);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("message", "이미 저장되어 있는 게시글 입니다.");
                return response;
            }
        }
    }

    @Transactional
    public boolean deleteFavoritePost(PostFavoriteDTO postFavoriteDTO) {
        // 저장되어 있는 게시글인지
        if(myFavoritePostRepository.existsByPostIdAndUserId(postFavoriteDTO.getPostId(), postFavoriteDTO.getUserId())){
            PostFavorite postFavorite = new PostFavorite(
                    postFavoriteDTO.getPostId(), postFavoriteDTO.getUserId()
            );
            myFavoritePostRepository.delete(postFavorite);
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public Object updateMyLocalArea(UsersDTO usersDTO) {
        Optional<Users> foundUser = userRepository.findById(usersDTO.getId());
        if(foundUser.isPresent()){
            Users user = foundUser.get();
            user.setLocalArea(usersDTO.getLocalArea());
            user.setLocalAreaAuthDate(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }

    /*public List<MyAdviceSummaryDTO> getMyAdvices(Integer userId) {
        List<MyAdviceSummaryDTO> myAdviceList = mypageRepository.findMyAdvices(userId);
        return  myAdviceList;
    }*/

    /*public Map<String, Object> getMyFavoritesData(Integer userId) {
        Map<String, Object> myFavoritesData = new HashMap<>();
        *//*myFavoritesData.put("locations", futureTrips);
        myFavoritesData.put("posts", pastTrips);*//*

        List<LocationDTO> myFavoriteLocationsList = locationRepository.getMyFavoriteLocationsData(userId);
        return null;
    }*/
}
