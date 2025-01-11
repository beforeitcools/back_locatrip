package com.ohgiraffers.jenkins_test_app.location.entity;

import com.ohgiraffers.jenkins_test_app.trip.entity.SelectedRegion;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "google_id", unique = true, nullable = false)
    private String googleId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "locationEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocationFavorite> favorites = new ArrayList<>();

    public Location() {
    }

    public Location(Integer id, String googleId, String name, String address, Double latitude, Double longitude, String category) {
        this.id = id;
        this.googleId = googleId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<LocationFavorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<LocationFavorite> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", googleId='" + googleId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category='" + category + '\'' +
                ", favorites=" + favorites +
                '}';
    }
}
