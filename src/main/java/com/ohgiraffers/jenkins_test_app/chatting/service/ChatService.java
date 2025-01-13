package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import com.ohgiraffers.jenkins_test_app.chatting.entity.Messages;
import com.ohgiraffers.jenkins_test_app.chatting.repository.ChatRepository;
import com.ohgiraffers.jenkins_test_app.common.utils.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService
{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private SecurityUtil securityUtil;

//    public void goOutAtChatroom(int chatroomId, int userId)
//    {
//        //채팅방 나가기
//        participateRepository.delete(new ParticipateMembers(userId, chatroomId));
//    }


//    public void insertParticipateMember(int chatroomId, int userId)
//    {
//        participateRepository.save(new ParticipateMembers(userId, chatroomId));
//    }

    public void searchChatsByKeyword(String keyword)
    {
        chatRepository.findByMessageContents(keyword);
        //chatroomRepository.findByChatroomName(keyword);
    }

    public void saveMessage(Messages message)
    {
        chatRepository.save(message);
    }

    public Messages saveAndGetMessage(Messages message){
        Messages savedMessage = chatRepository.save(message);
        return savedMessage;
    }

    public int getUnreadMessagesCount(int chatroomId)
    {
        // 안 읽은 메세지 카운트 가져오기
        // participate_member에서 가장 마지막에 있는 메세지 아이디 들고와서
        // 해당 아이디보다 큰 메세지들 (채팅룸 아이디 같음) 다 읽었다고 인서트 날려야 함
        // 만약에 마지막에 있는 메세지 아이디가 없으면
        // 0으로 넣어주고 시작
        System.out.println(" *************** STILL IN GET UNREAD MESSAGES COUNT FUNCTION. YOU ARE IN SERVICE ***************");
        Users authenticatedUser = securityUtil.getAuthenticatedUser();
        return chatRepository.countByReadStatus(chatroomId, authenticatedUser.getId());
    }

    @Transactional
    public void updateLastReadMessage(int chatroomId)
    {
        Integer latestMessageId = chatRepository.selectLatestMessageId(chatroomId);
        Users authenticatedUser = securityUtil.getAuthenticatedUser();

        if(latestMessageId == null || latestMessageId == 0){
            latestMessageId = 0;
        }
        chatRepository.updateLastReadMessageId(chatroomId, authenticatedUser.getId(), latestMessageId);
    }
}
