package com.ohgiraffers.jenkins_test_app.trip.controller;

import com.ohgiraffers.jenkins_test_app.trip.dto.TripNoteDTO;
import com.ohgiraffers.jenkins_test_app.trip.entity.TripNote;
import com.ohgiraffers.jenkins_test_app.trip.service.TripNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("memo")
public class TripNoteController {

    @Autowired
    private TripNoteService tripNoteService;

    @PostMapping("insertMemo")
    public ResponseEntity insertMemo(@RequestBody Map<String, Object> data){
        System.out.println("data = " + data);
        if(data == null || data.isEmpty()){
            return ResponseEntity.status(404).body("메모를 입력해주세요.");
        }
        TripNoteDTO memoDTO = new TripNoteDTO();
        memoDTO.setTripId((Integer) data.get("id"));
        memoDTO.setContent((String) data.get("content"));
        memoDTO.setDateIndex((Integer) data.get("dateIndex"));

        TripNote result = tripNoteService.addMemo(memoDTO);

        if(result == null){
            return ResponseEntity.status(500).body("메모 등록에 실패했습니다.");
        }
        System.out.println("result = " + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("selectMemo/{tripId}")
    public ResponseEntity selectMemo(@PathVariable("tripId") Integer tripId){

        List<TripNote> resultList = tripNoteService.searchMemo(tripId);
        if(resultList == null){
            return ResponseEntity.status(500).body("메모 등록에 실패했습니다.");
        }
        System.out.println("resultList = " + resultList);
        return ResponseEntity.ok(resultList);
    }

}
