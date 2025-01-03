package com.ohgiraffers.jenkins_test_app.chatting.model;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface ChatMapper
{
//    @Select("SELECT cr.id AS chatroom_id, cr.chatroom_name, m.message_contents, m.send_time FROM participate_members pm JOIN chat_room cr ON pm.chatroom_id = cr.id LEFT JOIN (SELECT chatroom_id, message_contents, send_time FROM (SELECT chatroom_id, message_contents, send_time, ROW_NUMBER() OVER (PARTITION BY chatroom_id ORDER BY send_time DESC) AS rn FROM messages) ranked WHERE rn = 1) m ON cr.id = m.chatroom_id WHERE pm.user_id = 1 ORDER BY m.send_time DESC")
//    List<RecentChatDTO> selectRecentMessages(); // 1 대신 이제 유저 아이디 가져와야 함
}
