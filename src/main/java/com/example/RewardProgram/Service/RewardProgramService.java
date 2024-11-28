package com.example.RewardProgram.Service;

import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Entity.TransactionData;
import com.example.RewardProgram.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardProgramService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Integer> calculateMonthlyRewards(CustomerData customerData) {
        Map<String, Integer> monthlyRewards = new HashMap<>();
        LocalDate start = LocalDate.now().minusMonths(3);
        LocalDate end = LocalDate.now();

        for (LocalDate date = start; date.isBefore(end); date = date.plusMonths(1)) {
            LocalDate monthStart = date.withDayOfMonth(1);
            LocalDate monthEnd = date.withDayOfMonth(date.lengthOfMonth());
            List<TransactionData> transactions = transactionRepository.findByCustomerDataAndDateOfTransactionBetween(customerData, monthStart, monthEnd);

            int points = transactions.stream()
                    .mapToInt(transaction -> calculatePoints(transaction.getAmountOfTransaction()))
                    .sum();

            monthlyRewards.put(monthStart.getMonth().toString(), points);
        }
        return monthlyRewards;
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += 2 * (amount - 100);
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }

}
