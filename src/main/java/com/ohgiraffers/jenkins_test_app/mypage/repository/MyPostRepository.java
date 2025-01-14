package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.advice.entity.Posts;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyFavoritePostSummaryDTO;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPostRepository extends JpaRepository<Posts, Integer> {

    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyPostSummaryDTO(
        p.id,
        p.title,
        p.contents,
        t.startDate,
        t.endDate,
        (MIN(sr.region)), 
        COUNT(DISTINCT sr.region)
    )
    FROM Posts p
    JOIN Trip t ON p.tripId = t.id
    LEFT JOIN SelectedRegion sr ON sr.tripEntity.id = t.id
    WHERE p.userId = :userId AND p.status = 1
    GROUP BY p.id
""")
    List<MyPostSummaryDTO> findMyPosts(@Param("userId") Integer userId);


    boolean existsByIdAndStatus(Integer postId, int i);
}
