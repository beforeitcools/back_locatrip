package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController
{
    @Autowired
    private ChatService chatService;

//TODO: 메세지 컨트롤러로 빼야하나 ...?
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public String handleMessage(String message)
//    {
//        return message;
//    }

    @GetMapping("/selectrecent")
    public List<Messages> selectAllChats()
    {
        List<Messages> messages = chatService.selectRecentMessages();
        return messages;
    }

    @GetMapping("/select/{chatroomId}")
    public List<Messages> selectChatsByUserId(@PathVariable("chatroomId") int chatroomId)
    {
        System.out.println("chatroomId = " + chatroomId);
        List<Messages> messages = chatService.selectChatsByChatroomId(chatroomId);
        return messages;
    }
}
