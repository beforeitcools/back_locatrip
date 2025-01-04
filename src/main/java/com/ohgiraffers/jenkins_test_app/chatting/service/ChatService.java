package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService
{
    @Autowired
    private ChatRepository chatRepository;

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
}
