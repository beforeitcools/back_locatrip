package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Messages
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "chatroom_id")
    private ChatRoom chatroom;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "message_contents")
    private String messageContents;

    @Column(name = "send_time")
    private String sendTime;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "readCount")
    private int readCount;

    public Messages()
    {
    }

    public Messages(int id, ChatRoom chatroom, int userId, String messageContents, String sendTime, boolean isRead, int readCount)
    {
        this.id = id;
        this.chatroom = chatroom;
        this.userId = userId;
        this.messageContents = messageContents;
        this.sendTime = sendTime;
        this.isRead = isRead;
        this.readCount = readCount;
    }

    public Messages(int id, ChatRoom chatroom, String messageContents, String sendTime, boolean isRead)
    {
        this.id = id;
        this.chatroom = chatroom;
        this.messageContents = messageContents;
        this.sendTime = sendTime;
        this.isRead = isRead;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ChatRoom getChatRoom()
    {
        return chatroom;
    }

    public void setChatRoom(ChatRoom chatRoom)
    {
        this.chatroom = chatRoom;
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

    public int getReadCount()
    {
        return readCount;
    }

    public void setReadCount(int readCount)
    {
        this.readCount = readCount;
    }
}
