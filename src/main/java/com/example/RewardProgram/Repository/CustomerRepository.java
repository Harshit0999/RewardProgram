package com.example.RewardProgram.Repository;

import com.example.RewardProgram.Entity.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerData, Long> {
}
