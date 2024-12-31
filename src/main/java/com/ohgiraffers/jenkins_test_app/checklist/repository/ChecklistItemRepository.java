package com.ohgiraffers.jenkins_test_app.checklist.repository;

import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Integer> {
    List<ChecklistItem> findByCategoryId(Integer categoryId);

    // 체크 상태 변경 메소드  // ci = ChecklistItem의 별칭
    @Modifying
    @Query("UPDATE ChecklistItem ci SET ci.isChecked = :isChecked WHERE ci.id = :id")
    void updateCheckedStatus(@Param("id") Integer id, @Param("isChecked") Boolean isChecked);
}
