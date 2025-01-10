package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.location.repository.LocationRepository;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyAdviceSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyPostRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyTripRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
