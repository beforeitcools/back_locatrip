package com.ohgiraffers.jenkins_test_app.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public String handleMessage(String message)
    {
        return message;
    }
}
