package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyTripSummary;
import com.ohgiraffers.jenkins_test_app.mypage.entity.MyTrip;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyTripRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MypageService {

    @Autowired
    MypageRepository mypageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyTripRepository myTripRepository;

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

    public List<MyTripSummary> getMyTrips(Integer userId) {
        List<MyTripSummary> myTripsOwnedByMe = myTripRepository.findTripsOwnedByUser(userId);
        List<MyTripSummary> myTripsWhereImMember = myTripRepository.findTripsWhereImMember(userId);

        List<MyTripSummary> allMyTrips = new ArrayList<>(myTripsOwnedByMe);
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
}
