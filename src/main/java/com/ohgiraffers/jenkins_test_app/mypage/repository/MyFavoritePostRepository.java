package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.advice.entity.PostFavorite;
import com.ohgiraffers.jenkins_test_app.mypage.dto.MyFavoritePostSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyFavoritePostRepository extends JpaRepository<PostFavorite, Integer> {

    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.mypage.dto.MyFavoritePostSummaryDTO(
        pf.postEntity.id,
        pf.postEntity.title,
        u.nickname,
        t.startDate,
        t.endDate,
        true
    )
    FROM PostFavorite pf
    JOIN Trip t ON pf.postEntity.tripId = t.id
    JOIN users_signup u ON pf.postEntity.userId = u.id
    WHERE pf.userId = :userId AND pf.postEntity.status = 1
""")
    List<MyFavoritePostSummaryDTO> getMyFavoritePostsData(@Param("userId") Integer userId);

    boolean existsByPostIdAndUserId(Integer postId, Integer userId);
}
