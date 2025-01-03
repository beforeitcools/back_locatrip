package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatRoom, Integer>
{
    void findByChatroomName(@Param("keyword") String keyword);
}
