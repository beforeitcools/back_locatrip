package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ChatRoom
{
    @Id
    private int id;

    @Column(name = "chatroom_name")
    private String chatroomName;

    @Column(name = "alarm")
    private boolean isAlarmOn;

    public ChatRoom()
    {
    }

    public ChatRoom(int id, String chatroomName, boolean isAlarmOn)
    {
        this.id = id;
        this.chatroomName = chatroomName;
        this.isAlarmOn = isAlarmOn;
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

    public boolean isAlarmOn()
    {
        return isAlarmOn;
    }

    public void setAlarmOn(boolean alarmOn)
    {
        isAlarmOn = alarmOn;
    }
}
