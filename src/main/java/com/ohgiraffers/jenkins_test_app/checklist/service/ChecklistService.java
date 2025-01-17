package com.ohgiraffers.jenkins_test_app.checklist.service;

import com.ohgiraffers.jenkins_test_app.checklist.dto.ChecklistCategoryDTO;
import com.ohgiraffers.jenkins_test_app.checklist.dto.ChecklistItemDTO;
import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistCategory;
import com.ohgiraffers.jenkins_test_app.checklist.entity.ChecklistItem;
import com.ohgiraffers.jenkins_test_app.checklist.repository.ChecklistCategoryRepository;
import com.ohgiraffers.jenkins_test_app.checklist.repository.ChecklistItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChecklistService {

    @Autowired
    private ChecklistCategoryRepository categoryRepository;

    @Autowired
    private ChecklistItemRepository itemRepository;

    @Autowired
    private ChecklistCategoryRepository checklistCategoryRepository;

    public List<ChecklistCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new ChecklistCategoryDTO(
                        category.getId(),
                        category.getTripId(),
                        category.getUserId(),
                        category.getName(),
                        category.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public Object addCategory(String categoryName, Integer tripId, Integer userId, Integer status) {

        ChecklistCategory category = new ChecklistCategory(tripId, userId, categoryName, status);
        ChecklistCategory savedCategory = categoryRepository.save(category);
        return savedCategory;
    }

    public List<ChecklistItemDTO> getItemsByCategory(Integer categoryId) {
        List<ChecklistItem> items = itemRepository.findByCategoryId(categoryId);
        return items.stream()
                .map(item -> new ChecklistItemDTO(
                        item.getId(),
                        item.getCategory().getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getChecked()
                ))
                .collect(Collectors.toList());
    }

    public Object addItemToCategory(Integer categoryId, String itemName){

     ChecklistCategory category = categoryRepository.findById(categoryId)
             .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

     ChecklistItem item = new ChecklistItem(
             category,
             itemName,
             null,
             false
     );

     ChecklistItem savedItem = itemRepository.save(item);

     return savedItem;
    }

    @Transactional
    public ChecklistCategory updateCategory(Integer categoryId, String newCategoryName) {
        // 카테고리 ID로 기존 카테고리 검색
        ChecklistCategory existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

        // 이름 수정
        existingCategory.setName(newCategoryName);

        // 수정된 카테고리를 저장
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public ChecklistItem updateItem(Integer itemId, String newName) {
        // Item ID로 기존 아이템 검색
        ChecklistItem existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        if (newName != null && !newName.isEmpty()) {
            existingItem.setName(newName);
        }

        // 수정된 아이템 저장
        return itemRepository.save(existingItem);
    }

    @Transactional
    public void updateItemCheckedStatus(Integer itemId, Boolean isChecked) {
        itemRepository.updateCheckedStatus(itemId, isChecked);
    }

   @Transactional
   public boolean deleteCategory(Integer categoryId) {
       ChecklistCategory category = categoryRepository.findById(categoryId)
               .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다."));

       if (category.getStatus() == 0) {
           throw new IllegalStateException("이미 삭제된 카테고리입니다.");
       }

       // status를 0으로 설정하여 삭제 상태로 변경
       category.setStatus(0);
       categoryRepository.save(category);
       return true;
   }

   @Transactional
    public void deleteItems(List<Integer> itemIds) {
        List<ChecklistItem> itemsToDelete = itemRepository.findAllById(itemIds);

        if (itemsToDelete.isEmpty()) {
            throw new IllegalArgumentException("삭제할 항목을 찾을 수 없습니다.");
        }

        itemRepository.deleteAll(itemsToDelete);
    }

    public List<Object[]> getRegionByTripId(int tripId) {
        return categoryRepository.findTripIdAndRegionByTripId(tripId);
    }

    public String calculateTripDuration(int tripId) {
        Object[] tripInfo = categoryRepository.findTripDatesByTripId(tripId);

        Object[] tripDates = (Object[]) tripInfo[0];

        if (tripDates == null || tripDates.length < 2) {
            throw new IllegalArgumentException("여행 날짜 정보를 찾을 수 없습니다.");
        }
        LocalDate startDate = LocalDate.parse(tripDates[0].toString());
        LocalDate endDate = LocalDate.parse(tripDates[1].toString());

        System.out.println(tripDates[0]);

        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        return nights + "박" + (nights + 1) + "일";
    }

}
