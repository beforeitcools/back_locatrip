package com.ohgiraffers.jenkins_test_app.mypage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_alarm")
public class UserAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // #1 첨삭받았을 때 #2 채택되었을 때 #3뱃지달았을때 #4 지역인증유효기간 만료
    @Column(name = "alarm_num", nullable = false)
    private int alarmNum;

    @Column(name = "is_read", nullable = false)
    private int isRead;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "local_advice_id")
    private Integer localAdviceId;

    public UserAlarm() {
    }

    public UserAlarm(Integer id, int alarmNum, int isRead, int userId, Integer localAdviceId) {
        this.id = id;
        this.alarmNum = alarmNum;
        this.isRead = isRead;
        this.userId = userId;
        this.localAdviceId = localAdviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAlarmNum() {
        return alarmNum;
    }

    public void setAlarmNum(int alarmNum) {
        this.alarmNum = alarmNum;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getLocalAdviceId() {
        return localAdviceId;
    }

    public void setLocalAdviceId(Integer localAdviceId) {
        this.localAdviceId = localAdviceId;
    }

    @Override
    public String toString() {
        return "UserAlarm{" +
                "id=" + id +
                ", alarmNum=" + alarmNum +
                ", isRead=" + isRead +
                ", userId=" + userId +
                ", localAdviceId=" + localAdviceId +
                '}';
    }
}
