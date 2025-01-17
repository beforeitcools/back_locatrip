package com.ohgiraffers.jenkins_test_app.mypage.repository;

import com.ohgiraffers.jenkins_test_app.advice.dto.PostDataDTO;
import com.ohgiraffers.jenkins_test_app.advice.dto.PostsWithMyLocalAreaDTO;
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

    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.advice.dto.PostsWithMyLocalAreaDTO(
        p.id,
        p.title,
        GROUP_CONCAT(sr.region)
    )
    FROM Posts p
    JOIN Trip t ON p.tripId = t.id
    LEFT JOIN SelectedRegion sr ON sr.tripId = t.id
    LEFT JOIN LocalAdvice la ON la.postId = p.id 
    WHERE :localArea IN (SELECT sr.region FROM SelectedRegion sr WHERE sr.tripId = p.tripId)
    AND p.status = 1
    GROUP BY p.id
    ORDER BY COUNT(la.id) DESC
""")*/

    @Query(value = """
    SELECT p.id,
           p.title,
           GROUP_CONCAT(DISTINCT sr.region) AS regions,
           COUNT(la.id) AS adviceCount
    FROM posts p
    JOIN trip t ON p.trip_id = t.id
    LEFT JOIN selected_region sr ON sr.trip_id = t.id
    LEFT JOIN local_advice la ON la.post_id = p.id 
    WHERE :localArea IN (SELECT sr.region FROM selected_region sr WHERE sr.trip_id = p.trip_id)
    AND p.status = 1
    GROUP BY p.id
    ORDER BY adviceCount DESC
    """, nativeQuery = true)
    List<Object[]> getPostWithMyLocalArea(String localArea);


    /*@Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.advice.dto.PostDataDTO(
        p.id,
        p.title,
        p.contents,
        p.createdAt,
        u.id,
        u.nickname,
        u.profilePic,
        t.startDate,
        t.endDate,
        GROUP_CONCAT(sr.region),
        COUNT(la.id)        
    )
    FROM Posts p
    JOIN Trip t ON p.tripId = t.id
    JOIN users_signup u ON p.userId = u.id
    JOIN SelectedRegion sr ON sr.tripId = t.id
    LEFT JOIN LocalAdvice la ON la.postId = p.id 
    WHERE p.status = 1
    GROUP BY p.id
    ORDER BY p.createdAt DESC
""")*/

    @Query(value = """
    SELECT p.id, p.title, p.contents, p.created_at, u.id AS userId, u.nickname, u.profile_pic,
           t.start_date, t.end_date,
           GROUP_CONCAT(DISTINCT sr.region) AS regions,
           COUNT(la.id) AS adviceCount
    FROM posts p
    JOIN trip t ON p.trip_id = t.id
    JOIN users u ON p.user_id = u.id
    JOIN selected_region sr ON sr.trip_id = t.id
    LEFT JOIN local_advice la ON la.post_id = p.id
    WHERE p.status = 1
    GROUP BY p.id
    ORDER BY p.created_at DESC
""", nativeQuery = true)
    List<Object[]> getAllPostData();


    boolean existsByIdAndStatus(Integer postId, int i);
}
