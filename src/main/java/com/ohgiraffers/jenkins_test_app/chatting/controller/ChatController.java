package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Messages handleMessage(Messages message)
    {
        // 데이터베이스에서 메세지 저장
        Messages savedMessage = chatRepository.save(message);
        return savedMessage;
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestBody Messages message)
    {
        chatRepository.save(message);
    }

    @PostMapping("/updateRoom/{chatroomId}")
    public void editChatroomName(@PathVariable("chatroomId") int chatroomId, @RequestBody String chatroomName)
    {
        System.out.println("room name change 하는 로직");
        chatService.updateChatroomName(chatroomId, chatroomName);
    }


}
