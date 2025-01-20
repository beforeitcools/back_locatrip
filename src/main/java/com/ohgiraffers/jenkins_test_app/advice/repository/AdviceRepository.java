package com.ohgiraffers.jenkins_test_app.advice.repository;

import com.ohgiraffers.jenkins_test_app.advice.dto.AdvicesWithUserInfoDTO;
import com.ohgiraffers.jenkins_test_app.advice.entity.LocalAdvice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdviceRepository extends JpaRepository<LocalAdvice, Integer> {

    @Query("SELECT COUNT(la) FROM LocalAdvice la WHERE la.userId = :userId AND la.isSelected = 1")
    Long countSelectedAdvicesByUser(@Param("userId") Integer userId);

    @Query("""
    SELECT new com.ohgiraffers.jenkins_test_app.advice.dto.AdvicesWithUserInfoDTO(
        la.id,
        la.contents,
        u.id,
        u.profilePic,
        u.nickname,
        la.createdAt
    )
    FROM LocalAdvice la
    JOIN users_signup u ON la.userId = u.id
    WHERE la.locationId = :locationId AND la.status = 1
    GROUP BY la.id
""")
    List<AdvicesWithUserInfoDTO> getAdvicesOnLocation(Integer locationId);


    @Query(value = "SELECT * FROM local_advice WHERE post_id = :postId AND user_id = :userId AND status = 1", nativeQuery = true)
    List<LocalAdvice> findByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId);



}
