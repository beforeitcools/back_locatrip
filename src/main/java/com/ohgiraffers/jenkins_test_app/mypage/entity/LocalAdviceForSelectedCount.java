package com.ohgiraffers.jenkins_test_app.mypage.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "local_advice")
public class LocalAdviceForSelectedCount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_id")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "advice_entire_id")
    private AdviceEntireForSelectedCount adviceEntireForSelectedCount;

    @Column(name = "is_selected")
    private int isSelected;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public LocalAdviceForSelectedCount() {
    }

    public LocalAdviceForSelectedCount(Integer id, Integer postId, AdviceEntireForSelectedCount adviceEntireForSelectedCount, int isSelected, LocalDateTime createdAt) {
        this.id = id;
        this.postId = postId;
        this.adviceEntireForSelectedCount = adviceEntireForSelectedCount;
        this.isSelected = isSelected;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public AdviceEntireForSelectedCount getAdviceEntireForSelectedCount() {
        return adviceEntireForSelectedCount;
    }

    public void setAdviceEntireForSelectedCount(AdviceEntireForSelectedCount adviceEntireForSelectedCount) {
        this.adviceEntireForSelectedCount = adviceEntireForSelectedCount;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LocalAdviceForSelectedCount{" +
                "id=" + id +
                ", postId=" + postId +
                ", adviceEntireForSelectedCount=" + adviceEntireForSelectedCount +
                ", isSelected=" + isSelected +
                ", createdAt=" + createdAt +
                '}';
    }
}
