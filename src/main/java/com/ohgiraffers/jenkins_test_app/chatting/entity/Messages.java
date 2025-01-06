package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Messages
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chatroom_id")
    private int chatroomId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "message_contents")
    private String messageContents;

    @Column(name = "send_time")
    private Timestamp sendTime;

    public Messages()
    {
    }

    public Messages(int id, int chatroomId, int userId, String messageContents, Timestamp sendTime)
    {
        this.id = id;
        this.chatroomId = chatroomId;
        this.userId = userId;
        this.messageContents = messageContents;
        this.sendTime = sendTime;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public Timestamp getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime)
    {
        this.sendTime = sendTime;
    }

    @Override
    public String toString()
    {
        return "Messages{" + "id=" + id + ", chatroomId=" + chatroomId + ", userId=" + userId + ", messageContents='" + messageContents + '\'' + ", sendTime=" + sendTime + '}';
    }
}
