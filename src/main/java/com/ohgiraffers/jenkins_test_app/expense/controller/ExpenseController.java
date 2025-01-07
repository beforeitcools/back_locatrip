package com.ohgiraffers.jenkins_test_app.expense.controller;

import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseDTO;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpensePaidByRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseParticipantsRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseRepository;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseService;
import com.ohgiraffers.jenkins_test_app.expense.service.ExpenseSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseSettlementService settlementService;


    @GetMapping("/grouped-by-days/{tripId}")
    public ResponseEntity<Map<String, Map<String, Object>>> getExpensesGroupedByDays(@PathVariable int tripId) {
        Map<String, Map<String, Object>> groupedExpenses = expenseService.getExpensesGroupedByTripDays(tripId);
        return ResponseEntity.ok(groupedExpenses);
    }

    @PostMapping("/insert")
    public ResponseEntity<Void> addExpense(@RequestBody ExpenseDTO expenseDTO) {
        Integer defaultTripId = 1;

        LocalDate date = expenseDTO.getDate();

        Expense expense = new Expense();
        expense.setTripId(defaultTripId);
        expense.setDate(date);
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());

        // 결제한 사람 설정
        List<Map<String, Object>> paidByDetails = new ArrayList<>();
        if (expenseDTO.getPaidByUsers() != null && !expenseDTO.getPaidByUsers().isEmpty()) {
            BigDecimal totalAmount = expenseDTO.getAmount();
            BigDecimal perPersonAmount = totalAmount.divide(
                    BigDecimal.valueOf(expenseDTO.getPaidByUsers().size()), BigDecimal.ROUND_HALF_UP);

            expenseDTO.getPaidByUsers().forEach(paidBy -> {
                Map<String, Object> paidByMap = new HashMap<>();
                paidByMap.put("userId", paidBy.getUserId());
                paidByMap.put("amount", perPersonAmount);
                paidByDetails.add(paidByMap);
            });
        }

        // 함께한 사람 설정 (1/n로 분할)
        List<Map<String, Object>> participantDetails = new ArrayList<>();
        if (expenseDTO.getParticipants() != null && !expenseDTO.getParticipants().isEmpty()) {
            BigDecimal totalAmount = expenseDTO.getAmount();
            BigDecimal perPersonAmount = totalAmount.divide(
                    BigDecimal.valueOf(expenseDTO.getParticipants().size()), BigDecimal.ROUND_HALF_UP);

            expenseDTO.getParticipants().forEach(participant -> {
                Map<String, Object> participantMap = new HashMap<>();
                participantMap.put("userId", participant.getUserId());
                participantMap.put("amount", perPersonAmount);
                participantDetails.add(participantMap);
            });
        }

        // Service 호출
        expenseService.saveExpenseWithDetails(expense, paidByDetails, participantDetails);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable int expenseId) {
        ExpenseDTO expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Void> updateExpense(
            @PathVariable int expenseId,
            @RequestBody ExpenseDTO expenseDTO) {
        expenseService.updateExpense(expenseId, expenseDTO);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/settlement/total")
    public ResponseEntity<Map<String, Object>> getTotalSettlement() {
        Map<String, Object> settlementDetails = settlementService.calculateTotalSettlement();
        return ResponseEntity.ok(settlementDetails);
    }


}
