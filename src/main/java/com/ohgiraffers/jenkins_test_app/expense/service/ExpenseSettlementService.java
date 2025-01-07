package com.ohgiraffers.jenkins_test_app.expense.service;

import com.ohgiraffers.jenkins_test_app.expense.entity.*;
import com.ohgiraffers.jenkins_test_app.expense.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseSettlementService {

    @Autowired
    private ExpensePaidByRepository expensePaidByRepository;
    @Autowired
    private ExpenseParticipantsRepository expenseParticipantsRepository;

    public Map<String, Object> calculateTotalSettlement() {
        Map<Integer, BigDecimal> paidByMap = new HashMap<>();
        Map<Integer, BigDecimal> participantMap = new HashMap<>();

        // 결제한 금액 합산
        List<ExpensePaidBy> paidByList = expensePaidByRepository.findAll();
        for (ExpensePaidBy paidBy : paidByList) {
            paidByMap.put(paidBy.getUserId(),
                    paidByMap.getOrDefault(paidBy.getUserId(), BigDecimal.ZERO)
                            .add(paidBy.getAmount()));
        }

        // 부담한 금액 합산
        List<ExpenseParticipants> participantsList = expenseParticipantsRepository.findAll();
        for (ExpenseParticipants participant : participantsList) {
            participantMap.put(participant.getUserId(),
                    participantMap.getOrDefault(participant.getUserId(), BigDecimal.ZERO)
                            .add(participant.getAmount()));
        }

        // 최종 정산 계산
        Map<Integer, BigDecimal> balanceMap = new HashMap<>();
        Set<Integer> allUsers = new HashSet<>();
        allUsers.addAll(paidByMap.keySet());
        allUsers.addAll(participantMap.keySet());

        for (Integer userId : allUsers) {
            BigDecimal paid = paidByMap.getOrDefault(userId, BigDecimal.ZERO);
            BigDecimal spent = participantMap.getOrDefault(userId, BigDecimal.ZERO);
            balanceMap.put(userId, paid.subtract(spent));
        }

        // 누가 누구에게 보내야 하는가 계산
        List<Map<String, Object>> transactions = new ArrayList<>();
        List<Map<String, Object>> userExpenses = new ArrayList<>();

        List<Map.Entry<Integer, BigDecimal>> creditors = balanceMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList());

        List<Map.Entry<Integer, BigDecimal>> debtors = balanceMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) < 0)
                .collect(Collectors.toList());

        for (Map.Entry<Integer, BigDecimal> creditor : creditors) {
            for (Map.Entry<Integer, BigDecimal> debtor : debtors) {
                if (creditor.getValue().compareTo(BigDecimal.ZERO) == 0 ||
                        debtor.getValue().compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }

                BigDecimal amount = creditor.getValue().min(debtor.getValue().abs());
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    transactions.add(Map.of(
                            "fromUser", debtor.getKey(),
                            "toUser", creditor.getKey(),
                            "amount", amount
                    ));
                    balanceMap.put(creditor.getKey(), creditor.getValue().subtract(amount));
                    balanceMap.put(debtor.getKey(), debtor.getValue().add(amount));
                }
            }
        }

        // 사용자별 총 지출 내역
        for (Integer userId : allUsers) {
            userExpenses.add(Map.of(
                    "userId", userId,
                    "paid", paidByMap.getOrDefault(userId, BigDecimal.ZERO),
                    "spent", participantMap.getOrDefault(userId, BigDecimal.ZERO)
            ));
        }

        return Map.of(
                "transactions", transactions,
                "userExpenses", userExpenses
        );
    }


}
