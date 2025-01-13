package com.ohgiraffers.jenkins_test_app.mypage.dto;

public class MyFavoriteLocationSummaryDTO {

    private String googleId;
    private String name;
    private String address;
    private boolean isFavorite;

    public MyFavoriteLocationSummaryDTO() {
    }

    public MyFavoriteLocationSummaryDTO(String googleId, String name, String address, boolean isFavorite) {
        this.googleId = googleId;
        this.name = name;
        this.address = address;
        this.isFavorite = isFavorite;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "MyFavoriteLocationSummaryDTO{" +
                "googleId='" + googleId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
