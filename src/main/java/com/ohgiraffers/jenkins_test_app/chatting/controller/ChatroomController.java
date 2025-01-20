package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.service.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatroom")
public class ChatroomController
{
    @Autowired
    private ChatroomService chatroomService;

    @PostMapping( "/update/{chatroomId}")
    public void editChatroomName(@PathVariable("chatroomId") int chatroomId, @RequestBody String chatroomName)
    {
        System.out.println("room name change 하는 로직");
        chatroomService.updateChatroomName(chatroomId, chatroomName);
    }

    @GetMapping("/search/{searchKeyword}")
    public List<RecentChatDTO> selectSearchChats(@PathVariable("searchKeyword") String searchKeyword)
    {
        return chatroomService.selectSearchMessagesByKeyword(searchKeyword);
    }

    @PostMapping( "/onetoone")
    public int createChatooom(@RequestBody Map<String, Object> userInfo)
    {
        // 새로운 채팅방 생성
        System.out.println("I got new chat room for one to one " + userInfo.get("userId"));
        return chatroomService.getOrCreateChatroom(userInfo);
    }

    @PostMapping("/createNewChatroom")
    public int createTripChatroom(@RequestBody Map<String, Object> tripInfo){
        return chatroomService.createTripChatroom(tripInfo);
    }

    @GetMapping("/getExistChatroom")
    public int getTripChatroom(@RequestBody Map<String, Object> tripInfo)
    {
        return chatroomService.getTripChatroom(tripInfo);
    }
}
