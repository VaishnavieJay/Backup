package com.natwest.currency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.currency.data.NWResponseBody;
import com.natwest.currency.data.NWResponseStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyDenominationControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void happyPath() throws Exception {
        final NWResponseBody<List<Integer>> responseBody = new NWResponseBody();
        responseBody.setStatus(NWResponseStatus.SUCCESS);
        responseBody.setResponseData(Arrays.asList(50, 50));
        responseBody.setTraceId(123456L);
        final String responseStr = objectMapper.writeValueAsString(responseBody);
        this.mockMvc.perform(get("/currency/denomination/100")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(responseStr));
    }

    @Test
    public void invalidRequest() throws Exception {
        this.mockMvc.perform(get("/currency/denomination/3.5")).andDo(print()).andExpect(status().isOk());
    }
}