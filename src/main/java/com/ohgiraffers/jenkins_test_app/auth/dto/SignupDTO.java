package com.ohgiraffers.jenkins_test_app.auth.dto;

public class SignupDTO {

    private Integer id;                         // 사용자 고유 ID
    private String nickname;                    // 사용자 닉네임
    private String userId;                      // 사용자 고유 아이디(이메일 형식)
    private String password;                    // 사용자 비밀번호 (암호화 되기전 user에게 받은 값)
    // private UserRole userRole;                    // 사용자 역할 (예: 'user', 'admin')
    private String profilePic;                  // 사용자 프로필 사진 URL

    private int status;                         // 사용자 계정 상태 (0: 비활성, 1: 활성)

    public SignupDTO() {
    }

    public SignupDTO(Integer id, String nickname, String userId, String password, String profilePic, int status) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.profilePic = profilePic;
        this.status = status;
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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SignupDTO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", status=" + status +
                '}';
    }
}
