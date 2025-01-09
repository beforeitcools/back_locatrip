package com.ohgiraffers.jenkins_test_app.mypage.service;

import com.ohgiraffers.jenkins_test_app.auth.dto.UsersDTO;
import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.mypage.entity.MyTrip;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MyTripRepository;
import com.ohgiraffers.jenkins_test_app.mypage.repository.MypageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public List<MyTrip> getMyTrips(Integer userId) {
        return myTripRepository.findMyTripsByUserId(userId);
    }

    public String deleteTrip(Integer tripId) {
        Object tripToDelete = myTripRepository.findById(tripId);
        if(Objects.isNull(tripToDelete)){
            return "삭제할 여행이 없습니다.";
        }
        myTripRepository.deleteById(tripId);
        return "삭제 성공";
    }
}
