package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatHistoryController
{
    @Autowired
    private ChatHistoryService chatHistoryService;

    @GetMapping("/recent")
    public List<RecentChatDTO> selectAllChats()
    {
        //List<RecentChatDTO> messages = chatService.selectRecentMessages();
        List<RecentChatDTO> messages = chatHistoryService.selectRecentMessagesByUserId();
        System.out.println(messages);
        return messages;
    }

    @GetMapping("/{chatroomId}")
    public List<Messages> selectChatsByUserId(@PathVariable("chatroomId") int chatroomId)
    {
        // 채팅방 내의 메세지들 불러오는 로직

        System.out.println("chatroomId = " + chatroomId);
        List<Messages> messages = chatHistoryService.selectChatsByChatroomId(chatroomId);
        return messages;
    }
}
