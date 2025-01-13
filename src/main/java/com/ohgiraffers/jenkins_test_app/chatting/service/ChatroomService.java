package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatroomService
{
    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public List<RecentChatDTO> selectSearchMessagesByKeyword(String keyword)
    {
        // 상대방 닉네임, 대화 내용에 키워드가 들어가는지 검사
        // 닉네임에 키워드 포함되어 있는 거 add,
        // 대화 내용에 포함되어 있는 거 add
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        List<RecentChatDTO> messages = chatroomRepository.selectSearchMessages(authenticatedUser.getId(), keyword);
        if(messages == null || messages.isEmpty()){
            return null;
        }
        return messages;
    }

    public void updateChatroomName(int chatroomId, String chatroomName)
    {
        Optional<ChatRoom> chatroomOptional = chatroomRepository.findById(chatroomId);
        if (chatroomOptional.isPresent()) {
            ChatRoom chatroom = chatroomOptional.get();
            chatroom.setChatroomName(chatroomName);
            chatroomRepository.save(chatroom);
        } else {
            throw new RuntimeException("Chatroom not found with ID: " + chatroomId);
        }
    }

    public void createNewChatroom(int chatroomId, String chatroomName)
    {
        //TODO 새로운 채팅방 생성
        // 1. 일정에서 가져오기
        //    chatroomName = 일정제목
        //    제일 처음 누르는 채팅방 들어가기 누르는 사람만 채팅방에 존재
        //    일대일, 일대다 가능

        // 2. 현지인과 대화
        //    chatroomName = 내가 대화하고 있는 사람 닉네임, 이름
        //    1:1 대화

        Optional<ChatRoom> chatroomOptional = chatroomRepository.findById(chatroomId);
        if (chatroomId == 0 ||chatroomOptional.isPresent()) {
            chatroomRepository.save(new ChatRoom(chatroomName));

        }
        else {

        }
    }


}
