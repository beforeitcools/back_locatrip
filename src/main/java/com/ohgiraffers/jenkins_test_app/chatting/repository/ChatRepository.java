package com.ohgiraffers.jenkins_test_app.chatting.repository;

import com.ohgiraffers.jenkins_test_app.chatting.dto.UnreadMessageCountDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface ChatRepository extends JpaRepository<Messages, Integer>
{
    void findByMessageContents(@Param("keyword") String keyword);

//    @Query(value = """
//        SELECT new com.ohgiraffers.jenkins_test_app.chatting.dto.UnreadMessageCountDTO(
//            cr.id,
//            m.userId,
//            CAST(COUNT(m.id) AS int)
//        )
//        FROM ChatRoom cr
//        JOIN Messages m ON cr.id = m.chatroom.id
//        JOIN ParticipateMembers pm ON cr.id = pm.chatroom.id
//        WHERE pm.userId = :userId AND m.id > pm.lastReadMessageId
//        GROUP BY cr.id, cr.chatroomName
//        """)
    @Query(value = """
        SELECT COUNT(m.id)
        FROM Messages m
        JOIN ChatRoom cr ON cr.id = m.chatroom.id
        JOIN ParticipateMembers pm ON cr.id = pm.chatroom.id
        WHERE pm.userId = :userId AND m.userId != :userId AND cr.id = :chatroomId
        AND m.id > pm.lastReadMessageId
    """)
     int countByReadStatus(@Param("chatroomId") int chatroomId, @Param("userId") int userId, boolean b);

    @Query(value = """
        UPDATE ParticipateMembers pm
        SET pm.lastReadMessageId = (SELECT m.id FROM Messages m WHERE m.chatroom.id = pm.chatroom.id ORDER BY m.sendTime DESC LIMIT 1)
        WHERE pm.userId = :userId AND pm.chatroom.id = :chatroomId
    """, nativeQuery = false)
    void updateLastReadMessageId(@PathVariable("chatroomId") int chatroomId, @Param("userId") int userId);
}
