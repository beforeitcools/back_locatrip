package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.dto.MessageDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Messages handleMessage(Messages message)
    {
        // 데이터베이스에서 메세지 저장
        return chatService.saveAndGetMessage(message);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestBody MessageDTO message)
    {
        chatService.saveMessage(message);
    }

    @PostMapping("/updateRoom/{chatroomId}")
    public void editChatroomName(@PathVariable("chatroomId") int chatroomId, @RequestBody String chatroomName)
    {
        System.out.println("room name change 하는 로직");
        chatService.updateChatroomName(chatroomId, chatroomName);
    }

    @PostMapping("/createChatooom")
    public void createChatooom()
    {
        // 새로운 채팅방 생성

    }

    @PostMapping("/goOut/{chatroomId}")
    public void goOutAtChatroom(@PathVariable("chatroomId") int chatroomId, int userId)
    {
//        System.out.println("채팅방 나가기");
//        chatService.goOutAtChatroom(chatroomId, userId);
    }
}
