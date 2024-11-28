package com.example.RewardProgram.Repository;

import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Entity.TransactionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionData, Long> {

    List<TransactionData> findByCustomerDataAndDateOfTransactionBetween(
            CustomerData customerData, LocalDate startDate, LocalDate endDate);

}
