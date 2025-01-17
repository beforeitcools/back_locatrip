package com.ohgiraffers.jenkins_test_app.chatting.service;

import com.ohgiraffers.jenkins_test_app.chatting.entity.EditState;
import com.ohgiraffers.jenkins_test_app.chatting.repository.EditingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditingService
{
    @Autowired
    private EditingRepository editingRepository;

    public EditState getEditingState(int tripId)
    {
        EditState editingState = editingRepository.getReferenceById(tripId);
        return editingState;
    }

    public void updateEditingState(int tripId, boolean state)
    {
        System.out.println("현재 상태는 " + state +"이구요 " + !state + "로 변경하겠습니다");
        editingRepository.save(new EditState(tripId, !state));
    }


}
