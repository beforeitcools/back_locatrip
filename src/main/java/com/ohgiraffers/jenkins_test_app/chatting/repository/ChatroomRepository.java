package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatRoom, Integer>
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

    void findByChatroomName(@Param("keyword") String keyword);
}
