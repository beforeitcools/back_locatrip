package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.*;

@Entity
public class ParticipateMembers
{
    @Id
    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn (name = "chatroom_id")
    private ChatRoom chatroom;

    @Column (name = "last_read_message_id")
    private int lastReadMessageId;

    public ParticipateMembers()
    {
    }

    public ParticipateMembers(int userId, ChatRoom chatroom)
    {
        this.userId = userId;
        this.chatroom = chatroom;
    }

    public ChatRoom getChatroom()
    {
        return chatroom;
    }

    public void setChatroom(ChatRoom chatroom)
    {
        this.chatroom = chatroom;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getLastReadMessageId()
    {
        return lastReadMessageId;
    }

    public void setLastReadMessageId(int lastReadMessageId)
    {
        this.lastReadMessageId = lastReadMessageId;
    }
}
