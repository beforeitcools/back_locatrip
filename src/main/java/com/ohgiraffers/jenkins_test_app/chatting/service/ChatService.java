package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.model.ChatMapper;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
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

//    public List<RecentChatDTO> selectRecentMessages()
//    {
//        System.out.println("걍 못 찾느 ㄴ다고?");
//        return chatMapper.selectRecentMessages();
//    }
}
