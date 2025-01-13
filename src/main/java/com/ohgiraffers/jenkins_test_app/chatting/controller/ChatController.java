package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private ChatService chatService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/unreadCount")
    public Messages handleMessage(Messages message)
    {
        System.out.println("저기요 이거 타세요? ChatController.handleMessage");
        return chatService.saveAndGetMessage(message);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public void sendMessage(@RequestBody Messages message)
    {
        // 데이터베이스에서 메세지 저장
        chatService.saveMessage(message);
        // 여기서 UnreadMessageCountDTO를 굳이 리턴해줄 필요가 있낭
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

    @RequestMapping(value = "/unread/count", method = RequestMethod.GET)
    public int getUnreadMessagesCount(@Param("chatroomId") int chatroomId)
    {
        System.out.println(" *************** GET UNREAD MESSAGES COUNT FUNCTION ***************");
        int unreadCount = chatService.getUnreadMessagesCount(chatroomId);
        return unreadCount;
    }

    @RequestMapping(value = "/updateLastMessage/{chatroomId}", method = RequestMethod.POST)
    public void updateUnreadMessageId(@PathVariable("chatroomId") int chatroomId)
    {
        System.out.println(" *************** UPDATE UNREAD MESSAGE ID FUNCTION ***************");
        chatService.updateLastReadMessage(chatroomId);
    }
}
