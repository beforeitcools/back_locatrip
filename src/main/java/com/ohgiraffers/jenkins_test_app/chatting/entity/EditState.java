package com.ohgiraffers.jenkins_test_app.chatting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class EditState
{
    @Id
    @Column(name = "trip_id")
    private int tripId;

    @Column(name = "is_editing")
    private boolean isEditing;

    public EditState()
    {
    }

    public EditState(int tripId, boolean isEditing)
    {
        this.tripId = tripId;
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

    public boolean isEditing()
    {
        return isEditing;
    }

    public void setEditing(boolean editing)
    {
        isEditing = editing;
    }

    @Override
    public String toString()
    {
        return "EditState{" + "tripId=" + tripId + ", isEditing=" + isEditing + '}';
    }
}
