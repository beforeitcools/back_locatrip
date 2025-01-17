package com.ohgiraffers.jenkins_test_app.expense.service;

import com.ohgiraffers.jenkins_test_app.auth.entity.Users;
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

    private String formatDate(LocalDate date) {
        return date.format(formatter);
    }

    public Map<String, Map<String, Object>> getExpensesGroupedByTripDays(int tripId) {
        List<Object[]> results = expenseRepository.findExpensesGroupedByTripDays(tripId);
        List<Object[]> preparationResults = expenseRepository.findPreparationExpenses(tripId);

        Object[] tripInfo = expenseRepository.findTripDatesByTripId(tripId);
        Object[] tripDates = (Object[]) tripInfo[0];

        if (tripDates == null || tripDates.length < 2) {
            throw new RuntimeException("해당 tripId의 여행일정이 제대로 설정 안되었습니다: " + tripId);
        }

        LocalDate startDate = LocalDate.parse(tripDates[0].toString());
        LocalDate endDate = LocalDate.parse(tripDates[1].toString());

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

            int expenseId = ((Number) row[3]).intValue();
            List<Map<String, Object>> participantsUser = participantsRepository.findParticipantsWithNickname(expenseId)
                    .stream()
                    .map(participant -> {
                        Map<String, Object> participantMap = new HashMap<>();
                        participantMap.put("id", participant[0]); // userId
                        participantMap.put("nickname", participant[1]); // 닉네임
                        return participantMap;
                    }).collect(Collectors.toList());
            expense.put("participants", participantsUser);

            preparationList.add(expense);
        }

        // 여행 날짜 생성
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            int dayNumber = (int) startDate.until(date).getDays() + 1;
            String dayKey = "day" + dayNumber;
            String formattedDate = dayKey + " " + formatDate(date);

            groupedExpenses.putIfAbsent(dayKey, new LinkedHashMap<>());
            Map<String, Object> dayExpenses = groupedExpenses.get(dayKey);
            dayExpenses.putIfAbsent("date", formattedDate);
            dayExpenses.putIfAbsent("expenses", new ArrayList<Map<String, Object>>());
        }

        for (Object[] row : results) {
            int dayNumber = ((Number) row[0]).intValue();
            LocalDate date = ((java.sql.Date) row[1]).toLocalDate();

            String category = (String) row[2];
            double amount = ((Number) row[3]).doubleValue();
            String description = (String) row[4];

            String dayKey = "day" + dayNumber;
            String formattedDate = dayKey + " " + formatDate(date);

            groupedExpenses.putIfAbsent(dayKey, new LinkedHashMap<>());
            Map<String, Object> dayExpenses = groupedExpenses.get(dayKey);

            dayExpenses.putIfAbsent("date", formattedDate);
            List<Map<String, Object>> expenses = (List<Map<String, Object>>) dayExpenses.getOrDefault("expenses", new ArrayList<>());

            Map<String, Object> expense = new HashMap<>();
            expense.put("id", ((Number) row[5]).intValue());
            expense.put("category", category);
            expense.put("amount", amount);
            expense.put("description", description);

            int expenseId = ((Number) row[5]).intValue();
            List<Map<String, Object>> participantsUser = participantsRepository.findParticipantsWithNickname(expenseId)
                    .stream()
                    .map(participant -> {
                        Map<String, Object> participantMap = new HashMap<>();
                        participantMap.put("id", participant[0]); // userId
                        participantMap.put("nickname", participant[1]); // 닉네임
                        return participantMap;
                    }).collect(Collectors.toList());
            expense.put("participants", participantsUser);

            expenses.add(expense);
            dayExpenses.put("expenses", expenses);
        }

        return groupedExpenses;
    }

    public Expense saveExpenseWithDetails(
            Expense expense,
            List<Map<String, Object>> paidByDetails,
            List<Map<String, Object>> participantDetails) {

        Expense savedExpense = expenseRepository.save(expense);

        if (paidByDetails != null) {
            for (Map<String, Object> detail : paidByDetails) {

                ExpensePaidBy paidBy = new ExpensePaidBy();
                paidBy.setExpense(savedExpense);

                // Users 엔티티 생성 및 매핑
                Users user = new Users();
                user.setId((Integer) detail.get("userId"));
                paidBy.setUser(user);

                paidBy.setAmount(new BigDecimal(detail.get("amount").toString()));
                paidByRepository.save(paidBy);
            }
        }

        if (participantDetails != null) {
            for (Map<String, Object> detail : participantDetails) {
                ExpenseParticipants participant = new ExpenseParticipants();
                participant.setExpense(savedExpense);

                // Users 엔티티 생성 및 매핑
                Users user = new Users();
                user.setId((Integer) detail.get("userId"));
                participant.setUser(user);

                participant.setAmount(new BigDecimal(detail.get("amount").toString()));
                participantsRepository.save(participant);
            }
        }
        return savedExpense;
    }


    public ExpenseDTO getExpenseById(int expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));


        List<ExpensePaidByDTO> paidByUsers = paidByRepository.findPaidByUsersWithNickname(expenseId)
                .stream()
                .map(row -> new ExpensePaidByDTO(
                        (Integer) row[0],
                        (String) row[1],
                        (String) row[2],
                        (BigDecimal) row[3]
                ))
                .collect(Collectors.toList());

        List<ExpenseParticipantsDTO> participants = participantsRepository.findParticipantsWithNickname(expenseId)
                .stream()
                .map(row -> new ExpenseParticipantsDTO(
                        (Integer) row[0],
                        (String) row[1],
                        (BigDecimal) row[2]
                ))
                .collect(Collectors.toList());

        return new ExpenseDTO(expense, paidByUsers, participants);
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

            Users user = new Users();
            user.setId(paidByDTO.getUserId());
            paidBy.setUser(user);

            paidBy.setAmount(paidByDTO.getAmount());
            expensePaidByRepository.save(paidBy);
        }

        // 3. ExpenseParticipants 수정
        expenseParticipantsRepository.deleteByExpenseId(expenseId);
        for (ExpenseParticipantsDTO participantDTO : expenseDTO.getParticipants()) {
            ExpenseParticipants participant = new ExpenseParticipants();
            participant.setExpense(expense);

            Users user = new Users();
            user.setId(participantDTO.getUserId());
            participant.setUser(user);

            participant.setAmount(participantDTO.getAmount());
            expenseParticipantsRepository.save(participant);
        }
    }

    public List<Map<String, Object>> getUsersByTripId(int tripId) {
        List<Object[]> results = expenseRepository.findUsersAndTripByTripId(tripId);
        return results.stream().map(row -> {
            Map<String, Object> user = new HashMap<>();
            user.put("id", row[0]); // userId
            user.put("nickname", row[1]); // 닉네임
            return user;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteExpense(int expenseId) {
        try {
            // 1. expense_paid_by 테이블에서 참조 레코드 삭제
            expensePaidByRepository.deleteByExpenseId(expenseId);

            // 2. expense_participants 테이블에서 참조 레코드 삭제
            expenseParticipantsRepository.deleteByExpenseId(expenseId);

            // 3. expense 테이블에서 레코드 삭제
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new RuntimeException("Expense not found"));

            expenseRepository.delete(expense);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete expense: " + e.getMessage());
        }
    }

    public List<Object[]> getRegionByTripId(int tripId) {
        return expenseRepository.findTripIdAndRegionByTripId(tripId);
    }

    public Map<String, Object> getTripDetails(int tripId) {
        List<Object[]> region = expenseRepository.findTripIdAndRegionByTripId(tripId);
        Object[] tripInfo = expenseRepository.findTripDatesByTripId(tripId);

        System.out.println("TripInfo for tripId " + tripId + ": " + Arrays.deepToString(new Object[]{tripInfo}));

        if (tripInfo == null || tripInfo.length == 0 || !(tripInfo[0] instanceof Object[])) {
            throw new RuntimeException("Trip details not found for tripId: " + tripId);
        }

        Object[] tripDates = (Object[]) tripInfo[0];

        if (tripDates.length < 2) {
            throw new RuntimeException("Incomplete trip dates for tripId: " + tripId);
        }

        Map<String, Object> tripDetails = new HashMap<>();
        tripDetails.put("startDate", tripDates[0].toString());
        tripDetails.put("endDate", tripDates[1].toString());
        tripDetails.put("region", region.stream().map(row -> row[0]).collect(Collectors.toList()));
        return tripDetails;
    }
}
