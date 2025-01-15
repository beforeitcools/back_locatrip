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

    @Column(name = "alarm")
    private int alarm;

    @Column(name = "is_for_trip")
    private boolean isForTrip;

    @Column(name = "status")
    private int status;

    public ChatRoom()
    {
    }

    public ChatRoom(String chatroomName)
    {
        this.chatroomName = chatroomName;
    }

    public ChatRoom(String chatroomName, boolean isForTrip)
    {
        this.chatroomName = chatroomName;
        this.isForTrip = isForTrip;
    }

    public ChatRoom(String chatroomName, int alarm, boolean isForTrip, int status)
    {
        this.chatroomName = chatroomName;
        this.alarm = alarm;
        this.isForTrip = isForTrip;
        this.status = status;
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

    public boolean isForTrip()
    {
        return isForTrip;
    }

    public void setForTrip(boolean forTrip)
    {
        isForTrip = forTrip;
    }

    public int getAlarm()
    {
        return alarm;
    }

    public void setAlarm(int alarm)
    {
        this.alarm = alarm;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
