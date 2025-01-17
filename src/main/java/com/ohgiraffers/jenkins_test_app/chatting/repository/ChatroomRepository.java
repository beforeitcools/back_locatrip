package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatroomRepository extends JpaRepository<ChatRoom, Integer>
{
    void findByChatroomName(@Param("keyword") String keyword);

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
        WHERE pm.userId = :userId AND (m.messageContents LIKE CONCAT("%",:keyword,"%") OR cr.chatroomName LIKE CONCAT("%",:keyword,"%"))
        ORDER BY m.sendTime DESC
    """, nativeQuery = false)
    List<RecentChatDTO> selectSearchMessages(@Param("userId")Integer userId, @PathVariable("keyword") String keyword);


    @Query(value = """
        SELECT cr FROM ChatRoom cr
        JOIN ParticipateMembers pm ON pm.chatroom.id = cr.id
        WHERE (pm.userId = :userId OR pm.userId = :myId)
            AND cr.isForTrip = false
            AND cr.status = 1
    """)
    Optional<ChatRoom> findByParticipants(int myId, int userId, boolean b);


    @Query("SELECT pm1.chatroom.id FROM ParticipateMembers pm1 JOIN ParticipateMembers pm2 ON pm1.chatroom.id = pm2.chatroom.id WHERE pm1.userId = :userId AND pm2.userId = :myId AND pm1.chatroom.isForTrip = false")
    Optional<Integer> findChatRoomIdByUserId(@Param("userId") int userId, @Param("myId") int myId);

    //ChatRoom selectExistingChatRoom();
}
