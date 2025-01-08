package com.ohgiraffers.jenkins_test_app.chatting.dto;

import java.sql.Timestamp;

public class RecentChatDTO
{
    private int chatroomId;
    private String chatroomName;
    private String currentMessage;
    private String sendTime;

    public RecentChatDTO()
    {
    }

    public RecentChatDTO(int chatroomId, String chatroomName, String currentMessage, String sendTime)
    {
        this.chatroomId = chatroomId;
        this.chatroomName = chatroomName;
        this.currentMessage = currentMessage;
        this.sendTime = sendTime;
    }

    public int getChatroomId()
    {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId)
    {
        this.chatroomId = chatroomId;
    }

    public String getChatroomName()
    {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName)
    {
        this.chatroomName = chatroomName;
    }

    public String getCurrentMessage()
    {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage)
    {
        this.currentMessage = currentMessage;
    }

    public String getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }

    @Override
    public String toString()
    {
        return "RecentChatDTO{" + "chatroomId=" + chatroomId + ", chatroomName='" + chatroomName + '\'' + ", currentMessage='" + currentMessage + '\'' + ", sendTime=" + sendTime + '}';
    }
}
