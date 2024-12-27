package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Messages, Integer>
{
    // 이렇게 쓰면 sql에서 쓰는 것처럼 쓸 수 있당
    @Query(value = "SELECT m.id, m.user_id, m.chatroom_id, m.message_contents, m.send_time FROM messages m JOIN (SELECT chatroom_id, MAX(send_time) AS latest_time FROM messages GROUP BY chatroom_id) lm ON m.chatroom_id = lm.chatroom_id AND m.send_time = lm.latest_time ORDER BY m.send_time DESC", nativeQuery = true)
    List<Messages> selectRecentMessages();

    @Query(value = "SELECT m FROM Messages m WHERE m.chatroomId = :chatroomId")
    List<Messages> selectChatsByChatroomId(@PathVariable("chatroomId") int chatroomId);
}
