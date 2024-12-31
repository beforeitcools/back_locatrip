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
                        category.getName()
                ))
                .collect(Collectors.toList());
    }

    public Object addCategory(String categoryName) {

        // 임시 trip, user id
        ChecklistCategory category = new ChecklistCategory(
                1,
                1,
                categoryName
        );

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
    public void updateItemCheckedStatus(Integer itemId, Boolean isChecked) {
        itemRepository.updateCheckedStatus(itemId, isChecked);
    }

    public Object updateCategory(String categoryName) {

        ChecklistCategory category = new ChecklistCategory(
                1,
                1,
                categoryName
        );

        category.setName(categoryName);

        ChecklistCategory savedCategory = categoryRepository.save(category);

        return savedCategory;
    }

   @Transactional
   public boolean deleteCategory(Integer categoryId) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("카테고리를 찾을 수 없습니다.");
        }

        categoryRepository.deleteById(categoryId);

        itemRepository.deleteById(categoryId);

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

}
