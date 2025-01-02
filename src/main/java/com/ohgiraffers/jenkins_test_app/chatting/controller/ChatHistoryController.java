package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController
{
    @Autowired
    private ChatService chatService;

    @GetMapping("/recent")
    public List<Messages> selectAllChats()
    {
        //List<RecentChatDTO> messages = chatService.selectRecentMessages();
        List<Messages> messages =chatService.selectRecentMessages();
        return messages;
    }

    @GetMapping("/{chatroomId}")
    public List<Messages> selectChatsByUserId(@PathVariable("chatroomId") int chatroomId)
    {
        System.out.println("chatroomId = " + chatroomId);
        List<Messages> messages = chatService.selectChatsByChatroomId(chatroomId);
        return messages;
    }
}
