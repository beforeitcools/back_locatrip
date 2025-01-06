package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class ParticipateMembers
{
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "chatroom_id")
    private int chatroomId;

    public ParticipateMembers()
    {
    }

    public ParticipateMembers(int userId, int chatroomId)
    {
        this.userId = userId;
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

    public int getChatroomId()
    {
        return chatroomId;
    }

    public void setChatroomId(int chatroomId)
    {
        this.chatroomId = chatroomId;
    }
}
