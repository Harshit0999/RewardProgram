package com.example.RewardProgram;

import com.example.RewardProgram.Controller.RewardProgramController;
import com.example.RewardProgram.Entity.CustomerData;
import com.example.RewardProgram.Repository.CustomerRepository;
import com.example.RewardProgram.Service.RewardProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RewardProgramControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RewardProgramService rewardService;

    @InjectMocks
    private RewardProgramController rewardProgramController;

    private CustomerData mockCustomer;

    @BeforeEach
    public void setup() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(rewardProgramController).build();

        // Mock customer data
        mockCustomer = new CustomerData();
        mockCustomer.setCustomerId(1L);
        mockCustomer.setCustomerName("Harshit Gupta");

        // Mock repository behavior
        Mockito.when(customerRepository.findById(1L)).thenReturn(java.util.Optional.of(mockCustomer));

        // Mock service behavior
        Map<String, Integer> mockRewards = new HashMap<>();
        mockRewards.put("AUGUST", 135);
        mockRewards.put("SEPTEMBER", 155);
        mockRewards.put("OCTOBER", 240);

        Mockito.when(rewardService.calculateMonthlyRewards(eq(mockCustomer))).thenReturn(mockRewards);
    }

    @Test
    public void testGetCustomerRewards_Success() throws Exception {
        mockMvc.perform(get("/rewards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer").value("Harshit Gupta"))
                .andExpect(jsonPath("$.monthlyRewards.AUGUST").value(135))
                .andExpect(jsonPath("$.monthlyRewards.SEPTEMBER").value(155))
                .andExpect(jsonPath("$.monthlyRewards.OCTOBER").value(240))
                .andExpect(jsonPath("$.totalRewards").value(530));
    }
}