package com.ohgiraffers.jenkins_test_app.chatting.controller;

import com.ohgiraffers.jenkins_test_app.chatting.entity.EditState;
import com.ohgiraffers.jenkins_test_app.chatting.service.EditingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/edit")
public class EditingStateController
{
    @Autowired
    private EditingService editingService;

    // 1. get 으로 해당 trip_id 넣고 검사해서 state 상태를 가져옴
    @GetMapping("/getState")
    public ResponseEntity<?> getEditingState(@RequestParam("tripId") int tripId)
    {
        try {
            EditState editingState = editingService.getEditingState(tripId);
            System.out.println("가져왔스: " + editingState);
            return ResponseEntity.ok(editingState.isEditing());
        } catch (EntityNotFoundException e) {
            System.err.println("Error: 아이디로 못 찾앗스: " + tripId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.err.println("Unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. post로 trip_id 넣고 검사해서 state 상태를 변경해줌
    @PostMapping("/updateState")
    public void updateEditingState(@RequestParam("tripId") int tripId, @RequestParam("state") boolean state)
    {
        editingService.updateEditingState(tripId, state);
    }

    // 3. 일정 생성할 때 미리 insert 해두는 것이 좋을 것 같습니다
}
