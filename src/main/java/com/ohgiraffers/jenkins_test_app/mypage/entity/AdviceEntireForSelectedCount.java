package com.ohgiraffers.jenkins_test_app.mypage.entity;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "my_selected_advice")
@Table(name = "advice_entire")
public class AdviceEntireForSelectedCount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "contents")
    private String contents;

    @OneToMany(mappedBy = "adviceEntireForSelectedCount")
    private List<LocalAdviceForSelectedCount> localAdviceForSelectedCounts;

    public AdviceEntireForSelectedCount() {
    }

    public AdviceEntireForSelectedCount(Integer id, Users user, String contents, List<LocalAdviceForSelectedCount> localAdviceForSelectedCounts) {
        this.id = id;
        this.user = user;
        this.contents = contents;
        this.localAdviceForSelectedCounts = localAdviceForSelectedCounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<LocalAdviceForSelectedCount> getLocalAdviceForSelectedCounts() {
        return localAdviceForSelectedCounts;
    }

    public void setLocalAdviceForSelectedCounts(List<LocalAdviceForSelectedCount> localAdviceForSelectedCounts) {
        this.localAdviceForSelectedCounts = localAdviceForSelectedCounts;
    }

    @Override
    public String toString() {
        return "AdviceEntireForSelectedCount{" +
                "id=" + id +
                ", user=" + user +
                ", contents='" + contents + '\'' +
                ", localAdviceForSelectedCounts=" + localAdviceForSelectedCounts +
                '}';
    }
}
