package com.example.RewardProgram;


import com.example.RewardProgram.Service.RewardProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RewardProgramApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RewardProgramService rewardsService;

    @Test
    public void testGetCustomerRewards() throws Exception {
        mockMvc.perform(get("/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer").value("Harshit gupta")) // Validate customer name
                .andExpect(jsonPath("$.totalRewards").value(530)) // Total rewards: Aug(175) + Sep(155) + Oct(200) = 530
                .andExpect(jsonPath("$.monthlyRewards.AUGUST").value(175)) // August rewards
                .andExpect(jsonPath("$.monthlyRewards.SEPTEMBER").value(155)) // September rewards
                .andExpect(jsonPath("$.monthlyRewards.OCTOBER").value(200)); // October rewards
    }

    @Test
    public void testCustomerNotFound() throws Exception {
        mockMvc.perform(get("/rewards/999")) // Non-existent customer
                .andExpect(status().is4xxClientError()); // Expect client error for missing customer
    }

}
