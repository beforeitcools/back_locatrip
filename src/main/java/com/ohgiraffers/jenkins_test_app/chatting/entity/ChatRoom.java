package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.*;

@Entity
public class ChatRoom
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "chatroom_name")
    private String chatroomName;

    public ChatRoom()
    {
    }

    public ChatRoom(String chatroomName)
    {
        this.chatroomName = chatroomName;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getChatroomName()
    {
        return chatroomName;
    }

    public void setChatroomName(String chatroomName)
    {
        this.chatroomName = chatroomName;
    }
}
