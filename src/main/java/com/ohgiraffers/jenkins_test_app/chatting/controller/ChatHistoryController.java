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

    @GetMapping("/recent/{userId}")
    public List<Messages> selectAllChats(@PathVariable("userId") int userId)
    {
        //List<RecentChatDTO> messages = chatService.selectRecentMessages();
        List<Messages> messages =chatService.selectRecentMessagesByUserId(userId);
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
