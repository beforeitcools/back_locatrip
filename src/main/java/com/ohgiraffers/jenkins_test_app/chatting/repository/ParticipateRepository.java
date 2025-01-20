package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.entity.ParticipateMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipateRepository extends JpaRepository<ParticipateMembers, Integer>
{

    @Query("SELECT COUNT(*) FROM ParticipateMembers p WHERE p.chatroom.id = :chatroomId AND p.userId = :userId")
    Optional<Integer> findUserExists(Integer chatroomId, Integer userId);
}
