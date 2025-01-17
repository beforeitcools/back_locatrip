package com.ohgiraffers.jenkins_test_app.chatting.dto;

public class EditingRequest
{
    private int tripId;
    private int userId;
    private boolean isEditing;

    public EditingRequest()
    {
    }

    public EditingRequest(int tripId, int userId, boolean isEditing)
    {
        this.tripId = tripId;
        this.userId = userId;
        this.isEditing = isEditing;
    }

    public int getTripId()
    {
        return tripId;
    }

    public void setTripId(int tripId)
    {
        this.tripId = tripId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public boolean isEditing()
    {
        return isEditing;
    }

    public void setEditing(boolean editing)
    {
        isEditing = editing;
    }
}
