package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Messages, Integer>
{
    void findByMessageContents(@Param("keyword") String keyword);

//    Integer getUnreadMessagesCount(int chatroomId, int userId);
}
