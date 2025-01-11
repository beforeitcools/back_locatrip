package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatHistoryRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatHistoryService
{
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public List<RecentChatDTO> selectRecentMessagesByUserId()
    {
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        // 테스트용 1
        List<RecentChatDTO> messages = chatHistoryRepository.selectRecentMessages(authenticatedUser.getId());
        if(messages == null || messages.isEmpty()){
            return null;
        }
        return messages;
    }

    public List<Messages> selectChatsByChatroomId(int chatroomId)
    {
        List<Messages> messages = chatHistoryRepository.selectChatsByChatroomId(chatroomId);
        return(messages == null || messages.isEmpty() ? null : messages);
    }
}
