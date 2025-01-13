package com.ohgiraffers.jenkins_test_app.chatting.dto;

public class UnreadMessageCountDTO
{
    private Integer chatroomId;
    private int recipientUserId;
    private int unreadCount;
    // 참여하고 있는 멤버 테이블에 추가해서 구별할 수 있나?


    public UnreadMessageCountDTO()
    {
    }

    public UnreadMessageCountDTO(int chatroomId, int recipientUserId, int unreadCount)
    {
        this.chatroomId = chatroomId;
        this.recipientUserId = recipientUserId;
        this.unreadCount = unreadCount;
    }

    public int getChatroomId()
    {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId)
    {
        this.chatroomId = chatroomId;
    }

    public int getRecipientUserId()
    {
        return recipientUserId;
    }

    public void setRecipientUserId(int recipientUserId)
    {
        this.recipientUserId = recipientUserId;
    }

    public int getUnreadCount()
    {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount)
    {
        this.unreadCount = unreadCount;
    }
}
