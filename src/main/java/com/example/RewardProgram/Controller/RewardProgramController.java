package com.example.RewardProgram.Controller;

import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Exception.CustomerNotFoundException;
import com.example.RewardProgram.Repository.CustomerRepository;
import com.example.RewardProgram.Service.RewardProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Map<String, Integer> monthlyRewards = rewardService.calculateMonthlyRewards(customer);
        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("customer", customer.getCustomerName());
        response.put("monthlyRewards", monthlyRewards);
        response.put("totalRewards", totalRewards);

        return response;
    }

    @GetMapping("/{customerId}/customize")
    public Map<String, Object> getCustomizedCustomerRewards(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "3") int months) {
        CustomerData customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(months);

        Map<String, Integer> monthlyRewards = rewardService.calculateRewardsForCustomPeriod(customer, start, end);
        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("customer", customer.getCustomerName());
        response.put("monthlyRewards", monthlyRewards);
        response.put("totalRewards", totalRewards);

        return response;
    }

}
