package com.ohgiraffers.jenkins_test_app.trip.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.auth.repository.UserRepository;
import com.ohgiraffers.jenkins_test_app.trip.entity.Trip;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripUsers;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripRepository;
import com.ohgiraffers.jenkins_test_app.trip.respository.TripUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripUsersService {

    @Autowired
    private TripUsersRepository tripUsersRepository;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TripUsers addTripUser(Integer tripId, Integer userId) {

        if(tripId == null || userId == null) {
            return null;
        }
        Trip trip = tripRepository.findById(tripId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);
        System.out.println("user = " + user);
        System.out.println("trip = " + trip);

        if (trip == null || user == null) {
            return null;
        }

        TripUsers tripUsers = new TripUsers();
        tripUsers.setTrip(trip);
        tripUsers.setUser(user);
        tripUsers = tripUsersRepository.save(tripUsers);

        return tripUsers;
    }


    public TripUsers isExsistTripUser(Integer tripId, Integer userId) {
        if(tripId == null || userId == null) {
            return null;
        }
        Trip trip = tripRepository.findById(tripId).orElse(null);
        Users user = userRepository.findById(userId).orElse(null);
        System.out.println("user = " + user);
        System.out.println("trip = " + trip);

        if (trip == null || user == null) {
            return null;
        }

        TripUsers tripUsers = tripUsersRepository.findByTripIdAndUserId(tripId, userId);
        return tripUsers;
    }
}
