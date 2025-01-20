package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.dto.RecentChatDTO;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ChatRoom;
import com.ohgiraffers.jenkins_test_app.chatting.entity.ParticipateMembers;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatroomRepository;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ParticipateRepository;
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
    private ParticipateRepository participateRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public List<RecentChatDTO> selectSearchMessagesByKeyword(String keyword)
    {
        // 상대방 닉네임, 대화 내용에 키워드가 들어가는지 검사
        // 닉네임에 키워드 포함되어 있는 거 add,
        // 대화 내용에 포함되어 있는 거 add
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        List<RecentChatDTO> messages = chatroomRepository.selectSearchMessages(authenticatedUser.getId(), keyword);
        if (messages == null || messages.isEmpty())
        {
            return null;
        }
        return messages;
    }

    public void updateChatroomName(int chatroomId, String chatroomName)
    {
        Optional<ChatRoom> chatroomOptional = chatroomRepository.findById(chatroomId);
        if (chatroomOptional.isPresent())
        {
            ChatRoom chatroom = chatroomOptional.get();
            chatroom.setChatroomName(chatroomName);
            chatroomRepository.save(chatroom);
        }
        else
        {
            throw new RuntimeException("Chatroom not found with ID: " + chatroomId);
        }
    }

//    @Transactional
//    public int getChatroom(Map<String, Object> userInfo) {
//        System.out.println("******* get Chat room function *****");
//
//        int chatroomId = 0;
//
//        // Validate input
//        if (userInfo.get("userId") != null) {
//            int userId = (Integer) userInfo.get("userId");
//
//            // Check if a chatroom already exists
//            Optional<ChatRoom> existingChatroom = getInExistChatroom(userId);
//            if (existingChatroom.isPresent()) {
//                System.out.println("존재하는 룸 타?");
//                chatroomId = existingChatroom.get().getId();
//                System.out.println("존재하는 거 맞긔  ??? ?" + chatroomId);
//            } else {
//                // Create a new chatroom if none exists
//                System.out.println("a만들어야하는 로직 타?");
//                chatroomId = createNewChatroom(userInfo.get("chatroomName").toString());
//            }
//        } else {
//            throw new IllegalArgumentException("User ID is required for creating or finding a chatroom.");
//        }
//
//        return chatroomId;
//    }
//
//    public Optional<ChatRoom> getInExistChatroom(int userId) {
//        System.out.println("************ 존재하는 채팅방 ***********");
//        return chatroomRepository.findByParticipants(securityUtil.getAuthenticatedUser().getId(), userId, false);
//    }
//
//    public int createNewChatroom(String chatroomName) {
//        System.out.println("************ 새로 만들어야 하는 채팅방!! ***********");
//
//        ChatRoom chatroom = new ChatRoom();
//        chatroom.setChatroomName(chatroomName);
//        chatroom.setAlarm(1);
//        chatroom.setForTrip(false);
//        chatroom.setStatus(1);
//
//        ChatRoom savedChatroom = chatroomRepository.save(chatroom);
//        return savedChatroom.getId();
//    }

    public int getOrCreateChatroom(Map<String, Object> userInfo)
    {
        System.out.println("******* get Chat room function *****");

        if (userInfo.get("userId") != null)
        {
            int userId = (Integer) userInfo.get("userId");
            String chatroomName = (String) userInfo.get("chatroomName");

            Optional<Integer> existingChatRoomId = chatroomRepository.findChatRoomIdByUserId(userId, securityUtil.getAuthenticatedUser().getId());

            if (existingChatRoomId.isPresent())
            {
                // 유저 아이디랑 일치하는 채팅방 잇는지 봐
                System.out.println(" Chat room already exists with ID: " + existingChatRoomId.get());
                return existingChatRoomId.get();
            }
            else {
                // 새 방 만들어
                ChatRoom newChatRoom = new ChatRoom(chatroomName, 1, false, 1); //String chatroomName, int alarm, boolean isForTrip, int status)
                chatroomRepository.save(newChatRoom);

                participateRepository.insertChatroomUser(userId, newChatRoom);
                participateRepository.insertChatroomUser(securityUtil.getAuthenticatedUser().getId(), newChatRoom);

                return newChatRoom.getId();
            }

        }
        else
        {
            throw new IllegalArgumentException("User ID is required for creating or finding a chatroom.");
        }
    }

    // 트립 쪽 채팅방 생성
    public int getTripChatroom(Map<String, Object> tripInfo)
    {
        int tripId = (Integer) tripInfo.get("id");
        String chatroomName = (String) tripInfo.get("title");

        // 이거는 trip Id가 있는지 봐야 되는디
        Optional<Integer> existingChatRoomId = chatroomRepository.findChatRoomIdByUserId(tripId, securityUtil.getAuthenticatedUser().getId());

        // find my user id exists in the chat room
        Optional<Integer> existingUserId = participateRepository.findUserExists(tripId, securityUtil.getAuthenticatedUser().getId());

        if(existingUserId.isEmpty())
        {
            ParticipateMembers chatRoomUser = new ParticipateMembers();
            chatRoomUser.setUserId(securityUtil.getAuthenticatedUser().getId());
            participateRepository.save(chatRoomUser);
        }

        return existingChatRoomId.get();
    }

    public int createTripChatroom(Map<String, Object> tripInfo)
    {
        int tripId = (Integer) tripInfo.get("id");
        int userId = securityUtil.getAuthenticatedUser().getId();
        String chatroomName = (String) tripInfo.get("title");

        // 새 방 만들어
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setForTrip(true);
        newChatRoom.setChatroomName(chatroomName);
        chatroomRepository.save(newChatRoom);

        ParticipateMembers chatRoomUser = new ParticipateMembers();
        chatRoomUser.setChatroom(newChatRoom);
        chatRoomUser.setUserId(userId);
        participateRepository.save(chatRoomUser);

        // save my Id too
        chatRoomUser.setUserId(securityUtil.getAuthenticatedUser().getId());
        participateRepository.save(chatRoomUser);

        return newChatRoom.getId();
    }
}

