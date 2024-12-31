package com.ohgiraffers.jenkins_test_app.checklist.controller;

import com.ohgiraffers.jenkins_test_app.checklist.dto.ChecklistCategoryDTO;
import com.ohgiraffers.jenkins_test_app.checklist.dto.ChecklistItemDTO;
import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistCategory;
import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistItem;
import com.ohgiraffers.jenkins_test_app.checklist.service.ChecklistService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {

    @Autowired
    private ChecklistService checklistService;

    @GetMapping("/categories")
    public ResponseEntity<List<ChecklistCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(checklistService.getAllCategories());
    }

    @PostMapping("/categories/insert")
    public ResponseEntity<String> addCategory(
            @RequestBody Map<String, String> requestBody) {
        String categoryName = requestBody.get("name");
        if (categoryName == null || categoryName.isEmpty()) {
            return ResponseEntity.badRequest().body("카테고리 이름을 입력해주세요.");
        }
        try{
            checklistService.addCategory(categoryName);
            return ResponseEntity.ok("카테고리가 추가되었습니다.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("카테고리 추가 실패");
        }
    }

    @PostMapping("/categories/update")
    public ResponseEntity<String> updateCategory(
            @RequestBody Map<String, String> requestBody){
                String categoryName = requestBody.get("name");
                if (categoryName == null || categoryName.isEmpty()) {
                    return ResponseEntity.badRequest().body("카테고리 이름을 입력해주세요.");
                }
                try{
                    checklistService.updateCategory(categoryName);
                    return ResponseEntity.ok("카테고리가 수정되었습니다.");
                } catch (Exception e){
                    return ResponseEntity.status(500).body("카테고리 추가 실패");
                }
    }

    @DeleteMapping("/categories/delete")
    public ResponseEntity<String> deleteCategory(
            @RequestBody Map<String, Integer> requestBody
    ) {

        Integer categoryId = requestBody.get("categoryId");

        if (categoryId == null) {
                return ResponseEntity.badRequest().body("카테고리 ID를 입력해주세요.");
        }

        try {
            boolean isDeleted = checklistService.deleteCategory(categoryId);

            if (isDeleted) {
                return ResponseEntity.ok("카테고리가 삭제되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("카테고리 삭제 실패");
        }
        return ResponseEntity.status(400).body("삭제할 수 없습니다.");
    }

    @GetMapping("/categories/{categoryId}/items")
    public ResponseEntity<List<ChecklistItemDTO>> getItemsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(checklistService.getItemsByCategory(categoryId));
    }

    @PostMapping("/categories/{categoryId}/items")
    public ResponseEntity<String> addItemToCategory(
            @PathVariable Integer categoryId,
            @RequestBody Map<String, String> requestBody) {
        String itemName = requestBody.get("name");
        if (itemName == null || itemName.isEmpty()) {
            return ResponseEntity.badRequest().body("아이템 이름을 입력해주세요.");
        }

        try {
            checklistService.addItemToCategory(categoryId, itemName);
            return ResponseEntity.ok("항목이 추가되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("아이템 추가 실패");
        }
    }

    @PostMapping("/items/{itemId}/check")
    public ResponseEntity<Void> updateItemCheckedStatus(
            @PathVariable Integer itemId,
            @RequestBody Boolean isChecked) {
        checklistService.updateItemCheckedStatus(itemId, isChecked);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/items/delete")
    public ResponseEntity<String> deleteItems(
            @RequestBody Map<String, List<Integer>> requestBody
    ) {
        List<Integer> itemIds = requestBody.get("itemIds");

        if (itemIds == null || itemIds.isEmpty()) {
            return ResponseEntity.badRequest().body("삭제할 아이템 ID 목록을 입력해주세요.");
        }

        try {
            checklistService.deleteItems(itemIds);
            return ResponseEntity.ok("선택한 항목이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("항목 삭제 실패: " + e.getMessage());
        }
    }
}
