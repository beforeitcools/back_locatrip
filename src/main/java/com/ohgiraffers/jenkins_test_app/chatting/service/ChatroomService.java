package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    @Transactional
    public int getChatroom(Map<String, Object> userInfo) {
        System.out.println("******* get Chat room function *****");

        int chatroomId = 0;

        // Validate input
        if (userInfo.get("userId") != null) {
            int userId = (Integer) userInfo.get("userId");

            // Check if a chatroom already exists
            Optional<ChatRoom> existingChatroom = getInExistChatroom(userId);
            if (existingChatroom.isPresent()) {
                System.out.println("존재하는 룸 타?");
                chatroomId = existingChatroom.get().getId();
                System.out.println("존재하는 거 맞긔  ??? ?" + chatroomId);
            } else {
                // Create a new chatroom if none exists
                System.out.println("a만들어야하는 로직 타?");
                chatroomId = createNewChatroom(userInfo.get("chatroomName").toString());
            }
        } else {
            throw new IllegalArgumentException("User ID is required for creating or finding a chatroom.");
        }

        return chatroomId;
    }

    public Optional<ChatRoom> getInExistChatroom(int userId) {
        System.out.println("************ 존재하는 채팅방 ***********");
        return chatroomRepository.findByParticipants(securityUtil.getAuthenticatedUser().getId(), userId, false);
    }

    public int createNewChatroom(String chatroomName) {
        System.out.println("************ 새로 만들어야 하는 채팅방!! ***********");

        ChatRoom chatroom = new ChatRoom();
        chatroom.setChatroomName(chatroomName);
        chatroom.setAlarm(1);
        chatroom.setForTrip(false);
        chatroom.setStatus(1);

        ChatRoom savedChatroom = chatroomRepository.save(chatroom);
        return savedChatroom.getId();
    }

//    public void getChatroom(Map<String, Object> userInfo)
//    {
//        //TODO 새로운 채팅방 생성
//        // 1. 일정에서 가져오기
//        //    chatroomName = 일정제목
//        //    제일 처음 누르는 채팅방 들어가기 누르는 사람만 채팅방에 존재
//        //    일대일, 일대다 가능
//
//        // 2. 현지인과 대화
//        //    chatroomName = 내가 대화하고 있는 사람 닉네임, 이름
//        //    1:1 대화
//
//        System.out.println("******* get Chat room function *****");
//        int userId = 0;
//        int chatroomId = 0;
//
//        if(userInfo.get("userId") != null){
//            userId = (Integer) userInfo.get("userId");
//            chatroomId = getInExistChatroom(userId);
//        }
//        else{
//           chatroomId = createNewChatroom(userInfo.get("chatroomName").toString());
//        }
//    }
//
//    public int getInExistChatroom(int userId)
//    {
//        System.out.println("************ TEST GET EXIST CHATROOM ***********");
//        // 일정 채팅방은 해당 trip이 chatroom 가지고 있는지 없는지 검사해서 있으면 그 chatroom 불러옴
//        // 일대일 채팅방은 어케 찾아올건데 ...
//        int existChatroom = chatroomRepository.findByUserId(securityUtil.getAuthenticatedUser().getId(), userId, false); // isForTrip = false, status = 1 이어야 함
//        return existChatroom;
//    }
//
//    public int createNewChatroom(String chatroomName)
//    {
//        System.out.println("************ TEST CHAT ROOM CREATE ***********");
//        ChatRoom chatroom = new ChatRoom(chatroomName, 1, false, 1);
//
//        ChatRoom savedChatroom = chatroomRepository.save(chatroom);
//        return savedChatroom.getId();
//    }


}
