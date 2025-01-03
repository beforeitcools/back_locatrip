package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ParticipateMembers;
import com.ohgiraffers.jenkins_test_app.chatting.model.ChatMapper;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ParticipateRepository;
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
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ParticipateRepository participateRepository;

//    @Autowired
//    private ChatMapper chatMapper;

    public List<Messages> selectRecentMessages()
    {
        List<Messages> messages = chatRepository.selectRecentMessages();
        if(messages == null || messages.isEmpty()){
            return null;
        }
        return messages;
    }

    public List<Messages> selectChatsByChatroomId(int chatroomId)
    {
        List<Messages> messages = chatRepository.selectChatsByChatroomId(chatroomId);
        return(messages == null || messages.isEmpty() ? null : messages);
    }

    public void updateChatroomName(int chatroomId, String chatroomName)
    {
        Optional<ChatRoom> chatroomOptional = chatroomRepository.findById(chatroomId);
        if (chatroomOptional.isPresent()) {
            ChatRoom chatroom = chatroomOptional.get();
            chatroom.setChatroomName(chatroomName);
            chatroomRepository.save(chatroom);
        } else {
            throw new RuntimeException("Chatroom not found with ID: " + chatroomId);
        }
    }

    public void goOutAtChatroom(int chatroomId, int userId)
    {
        //채팅방 나가기
        participateRepository.delete(new ParticipateMembers(userId, chatroomId));
    }

    public void createNewChatroom(int chatroomId, String chatroomName)
    {
        //새로운 채팅방 생성
        // 1. 일정에서 가져오기
        //    chatroomName = 일정제목
        //    제일 처음 누르는 채팅방 들어가기 누르는 사람만 채팅방에 존재
        //    일대일, 일대다 가능

        // 2. 현지인과 대화
        //    chatroomName = 내가 대화하고 있는 사람 닉네임, 이름
        //    1:1 대화

        Optional<ChatRoom> chatroomOptional = chatroomRepository.findById(chatroomId);
        if (chatroomId == 0 ||chatroomOptional.isPresent()) {
            chatroomRepository.save(new ChatRoom(chatroomName, true));

        }
        else {

        }
    }

    public void insertParticipateMember(int chatroomId, int userId)
    {
        participateRepository.save(new ParticipateMembers(userId, chatroomId));
    }

    public void searchAllInChatPage(String keyword)
    {
        chatRepository.findByMessageContents(keyword);
        chatroomRepository.findByChatroomName(keyword);
    }

//    public List<RecentChatDTO> selectRecentMessages()
//    {
//        System.out.println("걍 못 찾느 ㄴ다고?");
//        return chatMapper.selectRecentMessages();
//    }
}
