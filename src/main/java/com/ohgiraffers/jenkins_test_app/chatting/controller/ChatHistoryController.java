package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController
{
    @Autowired
    private ChatService chatService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/recent")
    public List<RecentChatDTO> selectAllChats()
    {
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        //List<RecentChatDTO> messages = chatService.selectRecentMessages();
        List<RecentChatDTO> messages =chatService.selectRecentMessagesByUserId(1);
        System.out.println(messages);
        return messages;
    }

    @GetMapping("/{chatroomId}")
    public List<Messages> selectChatsByUserId(@PathVariable("chatroomId") int chatroomId)
    {
        System.out.println("chatroomId = " + chatroomId);
        List<Messages> messages = chatService.selectChatsByChatroomId(chatroomId);
        return messages;
    }

//    @GetMapping("/unread/count")
//    public Integer getUnreadMessagesCount(int chatroomId, int userId){
//        return chatService.getUnreadMessagesCount(chatroomId, userId);
//    }
}
