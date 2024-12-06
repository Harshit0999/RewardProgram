package com.example.RewardProgram;

import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Entity.TransactionData;
import com.example.RewardProgram.Repository.TransactionRepository;
import com.example.RewardProgram.Service.RewardProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class RewardProgramServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardProgramService rewardProgramService;

    private CustomerData mockCustomer;

    @BeforeEach
    public void setup() {
        // Mock customer data
        mockCustomer = new CustomerData();
        mockCustomer.setCustomerId(1L);
        mockCustomer.setCustomerName("Harshit Gupta");
    }

    @Test
    public void testCalculateMonthlyRewards() {
        // Mock transactions for August
        List<TransactionData> augustTransactions = Arrays.asList(
                new TransactionData(1L, mockCustomer, LocalDate.of(2024, 8, 1), 120.00),
                new TransactionData(2L, mockCustomer, LocalDate.of(2024, 8, 15), 95.00)
        );

        // Mock transactions for September
        List<TransactionData> septemberTransactions = Arrays.asList(
                new TransactionData(3L, mockCustomer, LocalDate.of(2024, 9, 1), 75.00),
                new TransactionData(4L, mockCustomer, LocalDate.of(2024, 9, 20), 130.00)
        );

        // Mock transactions for October
        List<TransactionData> octoberTransactions = List.of(
                new TransactionData(5L, mockCustomer, LocalDate.of(2024, 10, 1), 150.00)
        );

        // Mock repository behavior
        Mockito.when(transactionRepository.findByCustomerDataAndDateOfTransactionBetween(eq(mockCustomer), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(augustTransactions) // August
                .thenReturn(septemberTransactions) // September
                .thenReturn(octoberTransactions); // October

        // Execute service method
        Map<String, Integer> monthlyRewards = rewardProgramService.calculateMonthlyRewards(mockCustomer);

        // Assert results
        Map<String, Integer> expectedRewards = new HashMap<>();
        expectedRewards.put("AUGUST", 135);
        expectedRewards.put("SEPTEMBER", 155);
        expectedRewards.put("OCTOBER", 240);

        assertEquals(expectedRewards, monthlyRewards);
    }

    @Test
    public void testCalculatePoints() {
        // Test point calculation logic
        int points1 = rewardProgramService.calculatePoints(120.00);
        int points2 = rewardProgramService.calculatePoints(75.00);
        int points3 = rewardProgramService.calculatePoints(45.00);

        // Assert points
        assertEquals(90, points1); // 2 × (20) + 1 × (50) = 90
        assertEquals(25, points2); // 1 × (25) = 25
        assertEquals(0, points3);  // No rewards below $50
    }
}
