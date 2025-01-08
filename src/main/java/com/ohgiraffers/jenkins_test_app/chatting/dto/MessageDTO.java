package com.ohgiraffers.jenkins_test_app.chatting.dto;

public class MessageDTO
{
    //chatroomId: 1, messageContents: gg, sendTime: 2025-01-08T12:43:42.454, read: false
    private int chatroomId;
    private int userId;
    private String messageContents;
    private String sendTime;
    private boolean isRead;

    public MessageDTO()
    {
    }

    public MessageDTO(int chatroomId, String messageContents, String sendTime, boolean isRead)
    {
        this.chatroomId = chatroomId;
        this.messageContents = messageContents;
        this.sendTime = sendTime;
        this.isRead = isRead;
    }

    public int getChatroomId()
    {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId)
    {
        this.chatroomId = chatroomId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getMessageContents()
    {
        return messageContents;
    }

    public void setMessageContents(String messageContents)
    {
        this.messageContents = messageContents;
    }

    public String getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void setRead(boolean read)
    {
        isRead = read;
    }


}
