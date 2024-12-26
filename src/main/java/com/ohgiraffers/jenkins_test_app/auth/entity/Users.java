package com.ohgiraffers.jenkins_test_app.auth.entity;

import com.ohgiraffers.jenkins_test_app.auth.Enum.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "users_signup")
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "profile_pic")
    private String profilePic;

    @Column(name = "local_area")
    private String localArea;

    @Column(name = "local_area_auth_date")
    private LocalDateTime localAreaAuthDate;

    @Column(name = "own_badge")
    private int ownBadge;

    @Column(name = "status")
    private int status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "inactive_at")
    private LocalDateTime inactiveAt;

    public Users() {
    }

    public Users(Integer id, String nickname, String userId, String password, UserRole role, String profilePic, String localArea, LocalDateTime localAreaAuthDate, int ownBadge, int status, LocalDateTime createdAt, LocalDateTime inactiveAt) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.profilePic = profilePic;
        this.localArea = localArea;
        this.localAreaAuthDate = localAreaAuthDate;
        this.ownBadge = ownBadge;
        this.status = status;
        this.createdAt = createdAt;
        this.inactiveAt = inactiveAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLocalArea() {
        return localArea;
    }

    public void setLocalArea(String localArea) {
        this.localArea = localArea;
    }

    public LocalDateTime getLocalAreaAuthDate() {
        return localAreaAuthDate;
    }

    public void setLocalAreaAuthDate(LocalDateTime localAreaAuthDate) {
        this.localAreaAuthDate = localAreaAuthDate;
    }

    public int getOwnBadge() {
        return ownBadge;
    }

    public void setOwnBadge(int ownBadge) {
        this.ownBadge = ownBadge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getInactiveAt() {
        return inactiveAt;
    }

    public void setInactiveAt(LocalDateTime inactiveAt) {
        this.inactiveAt = inactiveAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", profilePic='" + profilePic + '\'' +
                ", localArea='" + localArea + '\'' +
                ", localAreaAuthDate=" + localAreaAuthDate +
                ", ownBadge=" + ownBadge +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", inactiveAt=" + inactiveAt +
                '}';
    }
}
