package com.ohgiraffers.jenkins_test_app.trip.dto;

public class TripNoteDTO {

    Integer id;
    Integer tripId;
    String content;
    int dateIndex;

    public TripNoteDTO() {
    }

    public TripNoteDTO(Integer id, Integer tripId, String content, int dateIndex) {
        this.id = id;
        this.tripId = tripId;
        this.content = content;
        this.dateIndex = dateIndex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(int dateIndex) {
        this.dateIndex = dateIndex;
    }

    @Override
    public String toString() {
        return "TripNoteDTO{" +
                "id=" + id +
                ", tripId=" + tripId +
                ", content='" + content + '\'' +
                ", dateIndex=" + dateIndex +
                '}';
    }
}
