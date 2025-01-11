package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.dto.MessageDTO;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Messages, Integer>
{
    void findByMessageContents(@Param("keyword") String keyword);

//    Integer getUnreadMessagesCount(int chatroomId, int userId);
}
