package com.ohgiraffers.jenkins_test_app.chatting.dto;

public class RecentChatDTO
{
    private String chatroomId;
    private String chatroomName;
    private String chatroomPic;
    private String currentMessage;
    private String sendTime;
    private boolean isRead;

    public RecentChatDTO()
    {
    }

    public RecentChatDTO(String chatroomId, String chatroomName, String chatroomPic, String currentMessage, boolean isRead)
    {
        this.chatroomId = chatroomId;
        this.chatroomName = chatroomName;
        this.chatroomPic = chatroomPic;
        this.currentMessage = currentMessage;
        this.isRead = isRead;
    }

    public String getChatroomId()
    {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId)
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

    public String getChatroomPic()
    {
        return chatroomPic;
    }

    public void setChatroomPic(String chatroomPic)
    {
        this.chatroomPic = chatroomPic;
    }

    public String getCurrentMessage()
    {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage)
    {
        this.currentMessage = currentMessage;
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
