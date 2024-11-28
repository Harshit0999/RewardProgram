package com.example.RewardProgram.Controller;

import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Repository.CustomerRepository;
import com.example.RewardProgram.Service.RewardProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
public class RewardProgramController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RewardProgramService rewardService;

    @GetMapping("/{customerId}")
    public Map<String, Object> getCustomerRewards(@PathVariable Long customerId) {
        CustomerData customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Map<String, Integer> monthlyRewards = rewardService.calculateMonthlyRewards(customer);
        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("customer", customer.getCustomerName());
        response.put("monthlyRewards", monthlyRewards);
        response.put("totalRewards", totalRewards);

        return response;
    }

}
