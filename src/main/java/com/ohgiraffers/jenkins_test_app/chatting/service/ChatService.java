package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.MessageDTO;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ParticipateMembers;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ParticipateRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService
{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SecurityUtil securityUtil;

//    public void goOutAtChatroom(int chatroomId, int userId)
//    {
//        //채팅방 나가기
//        participateRepository.delete(new ParticipateMembers(userId, chatroomId));
//    }


//    public void insertParticipateMember(int chatroomId, int userId)
//    {
//        participateRepository.save(new ParticipateMembers(userId, chatroomId));
//    }

    public void searchChatsByKeyword(String keyword)
    {
        chatRepository.findByMessageContents(keyword);
        //chatroomRepository.findByChatroomName(keyword); // 컨트롤러에서 해줘야지!!
    }

    public void saveMessage(Messages message)
    {
        chatRepository.save(message);
    }

    public Messages saveAndGetMessage(Messages message){
        Messages savedMessage = chatRepository.save(message);
        return savedMessage;
    }

    public Integer getUnreadMessagesCount(int chatroomId)
    {
        // 안 읽은 메세지 가져오기
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        //return chatRepository.countByReadStatus(chatroomId, authenticatedUser.getId(), false);
        return 1;
    }

    public void updateReadState(int chatroomId){

    }

//    public List<RecentChatDTO> selectRecentMessages()
//    {
//        System.out.println("걍 못 찾느 ㄴ다고?");
//        return chatMapper.selectRecentMessages();
//    }
}
