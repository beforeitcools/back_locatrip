package com.ohgiraffers.jenkins_test_app.expense.service;

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
        Map<Integer, String> userNicknameMap = new HashMap<>(); // 닉네임 저장

        // 결제한 금액 합산 및 닉네임 매핑
        List<Object[]> paidByList = expensePaidByRepository.findAllPaidByWithNickname();
        for (Object[] row : paidByList) {
            Integer userId = (Integer) row[0];
            String nickname = (String) row[1];
            BigDecimal amount = (BigDecimal) row[2];

            userNicknameMap.put(userId, nickname); // 닉네임 저장
            paidByMap.put(userId, paidByMap.getOrDefault(userId, BigDecimal.ZERO).add(amount));
        }

        // 부담한 금액 합산 및 닉네임 매핑
        List<Object[]> participantsList = expenseParticipantsRepository.findAllParticipantsWithNickname();
        for (Object[] row : participantsList) {
            Integer userId = (Integer) row[0];
            String nickname = (String) row[1];
            BigDecimal amount = (BigDecimal) row[2];

            userNicknameMap.put(userId, nickname); // 닉네임 저장
            participantMap.put(userId, participantMap.getOrDefault(userId, BigDecimal.ZERO).add(amount));
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
                            "fromUserId", debtor.getKey(),  // flutter에서 현재 로그인된 userId를 갖고오기 위한 키 추가
                            "toUserId", creditor.getKey(),
                            "fromNickname", userNicknameMap.get(debtor.getKey()), // 닉네임 사용
                            "toNickname", userNicknameMap.get(creditor.getKey()), // 닉네임 사용
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
                    "nickname", userNicknameMap.get(userId), // 닉네임 추가
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
