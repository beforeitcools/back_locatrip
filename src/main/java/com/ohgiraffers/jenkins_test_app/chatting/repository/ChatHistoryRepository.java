package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatRoom, Integer>
{
    // 이렇게 쓰면 sql에서 쓰는 것처럼 쓸 수 있당
    @Query(value = """
        SELECT new com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO(
            cr.id,
            cr.chatroomName,
            m.messageContents,
            m.sendTime
        )
        FROM ParticipateMembers pm
        JOIN pm.chatroom cr
        LEFT JOIN (
            SELECT m.chatroom.id AS chatRoomId, m.messageContents AS messageContents, m.sendTime AS sendTime
            FROM Messages m
            WHERE m.sendTime = (
                SELECT MAX(m2.sendTime)
                FROM Messages m2
                WHERE m2.chatroom.id = m.chatroom.id
            )
        ) m ON cr.id = m.chatRoomId
        WHERE pm.userId = :userId
        ORDER BY m.sendTime DESC
    """, nativeQuery = false)
    List<RecentChatDTO> selectRecentMessages(@Param("userId") int userId);

    @Query(value = "SELECT m FROM Messages m WHERE m.chatroom.id = :chatroomId")
    List<Messages> selectChatsByChatroomId(@PathVariable("chatroomId") int chatroomId);
}
