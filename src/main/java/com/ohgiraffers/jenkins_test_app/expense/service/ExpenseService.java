package com.ohgiraffers.jenkins_test_app.expense.service;

import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseDTO;
import com.ohgiraffers.jenkins_test_app.expense.dto.ExpensePaidByDTO;
import com.ohgiraffers.jenkins_test_app.expense.dto.ExpenseParticipantsDTO;
import com.ohgiraffers.jenkins_test_app.expense.entity.Expense;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpensePaidBy;
import com.ohgiraffers.jenkins_test_app.expense.entity.ExpenseParticipants;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpensePaidByRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseParticipantsRepository;
import com.ohgiraffers.jenkins_test_app.expense.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpensePaidByRepository paidByRepository;

    @Autowired
    private ExpenseParticipantsRepository participantsRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd/E", Locale.KOREAN);
    @Autowired
    private ExpensePaidByRepository expensePaidByRepository;
    @Autowired
    private ExpenseParticipantsRepository expenseParticipantsRepository;

    private String formatDate (LocalDate date) {
        return date.format(formatter);
    }

    public Map<String, Map<String, Object>> getExpensesGroupedByTripDays(int tripId) {
        List<Object[]> results = expenseRepository.findExpensesGroupedByTripDays(tripId);
        List<Object[]> preparationResults = expenseRepository.findPreparationExpenses(tripId);

        Map<String, Map<String, Object>> groupedExpenses = new LinkedHashMap<>();

        // 여행 준비 섹션
        Map<String, Object> preparationExpenses = new HashMap<>();
        preparationExpenses.put("date", "여행 준비");
        preparationExpenses.put("expenses", new ArrayList<Map<String, Object>>());
        groupedExpenses.put("preparation", preparationExpenses);

        // 여행 준비 항목
        List<Map<String, Object>> preparationList = (List<Map<String, Object>>) preparationExpenses.get("expenses");
        for (Object[] row : preparationResults) {
            String category = (String) row[0];
            double amount = ((Number) row[1]).doubleValue();
            String description = (String) row[2];

            Map<String, Object> expense = new LinkedHashMap<>();
            expense.put("id", ((Number) row[3]).intValue());
            expense.put("category", category);
            expense.put("amount", amount);
            expense.put("description", description);

            preparationList.add(expense);
        }


        for (Object[] row : results) {
            int dayNumber = ((Number) row[0]).intValue();
            LocalDate date = ((java.sql.Date) row[1]).toLocalDate();
            String formattedDate = formatDate(date);

            String category = (String) row[2];
            double amount = ((Number) row[3]).doubleValue();
            String description = (String) row[4];

            String dayKey = "day" + dayNumber;
            groupedExpenses.putIfAbsent(dayKey, new LinkedHashMap<>());
            Map<String, Object> dayExpenses = groupedExpenses.get(dayKey);

            dayExpenses.putIfAbsent("date", formattedDate);
            List<Map<String, Object>> expenses = (List<Map<String, Object>>) dayExpenses.getOrDefault("expenses", new ArrayList<>());

            Map<String, Object> expense = new HashMap<>();
            expense.put("id", ((Number) row[5]).intValue());
            expense.put("category", category);
            expense.put("amount", amount);
            expense.put("description", description);

            expenses.add(expense);
            dayExpenses.put("expenses", expenses);
        }

        return groupedExpenses;
    }

    public Expense saveExpenseWithDetails(
            Expense expense,
            List<Map<String, Object>> paidByDetails,
            List<Map<String, Object>> participantDetails){

        Expense savedExpense = expenseRepository.save(expense);

        if (paidByDetails != null) {
            for (Map<String, Object> detail : paidByDetails) {
                ExpensePaidBy paidBy = new ExpensePaidBy();
                paidBy.setExpense(savedExpense);
                paidBy.setUserId((Integer) detail.get("userId"));
                paidBy.setAmount(new BigDecimal(detail.get("amount").toString()));
                paidByRepository.save(paidBy);
            }
        }

        if (participantDetails != null) {
            for (Map<String, Object> detail : participantDetails) {
                ExpenseParticipants participant = new ExpenseParticipants();
                participant.setExpense(savedExpense);
                participant.setUserId((Integer) detail.get("userId"));
                participant.setAmount(new BigDecimal(detail.get("amount").toString()));
                participantsRepository.save(participant);
            }
        }
        return savedExpense;
    }

    public ExpenseDTO getExpenseById(int expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        List<ExpensePaidByDTO> paidByUsers = expensePaidByRepository.findByExpenseId(expenseId)
                .stream()
                .map(paidBy -> new ExpensePaidByDTO(paidBy.getUserId(), paidBy.getAmount()))
                .collect(Collectors.toList());

        List<ExpenseParticipantsDTO> participants = expenseParticipantsRepository.findByExpenseId(expenseId)
                .stream()
                .map(participant -> new ExpenseParticipantsDTO(participant.getUserId(), participant.getAmount()))
                .collect(Collectors.toList());

        ExpenseDTO expenseDTO = new ExpenseDTO(expense, paidByUsers, participants);
        expenseDTO.setId(expense.getId());
        return expenseDTO;
    }

    @Transactional
    public void updateExpense(int expenseId, ExpenseDTO expenseDTO) {
        // 1. Expense 수정
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setDate(expenseDTO.getDate());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());
        expenseRepository.save(expense);

        // 2. ExpensePaidBy 수정
        expensePaidByRepository.deleteByExpenseId(expenseId);
        for (ExpensePaidByDTO paidByDTO : expenseDTO.getPaidByUsers()) {
            ExpensePaidBy paidBy = new ExpensePaidBy();
            paidBy.setExpense(expense);
            paidBy.setUserId(paidByDTO.getUserId());
            paidBy.setAmount(paidByDTO.getAmount());
            expensePaidByRepository.save(paidBy);
        }

        // 3. ExpenseParticipants 수정
        expenseParticipantsRepository.deleteByExpenseId(expenseId);
        for (ExpenseParticipantsDTO participantDTO : expenseDTO.getParticipants()) {
            ExpenseParticipants participant = new ExpenseParticipants();
            participant.setExpense(expense);
            participant.setUserId(participantDTO.getUserId());
            participant.setAmount(participantDTO.getAmount());
            expenseParticipantsRepository.save(participant);
        }
    }

}
