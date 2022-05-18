package com.natwest.currency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyDenominationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void happyPath() throws Exception {
        this.mockMvc.perform(get("/currency/denomination/100")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[50,50]"));
    }

    @Test
    public void invalidRequest() throws Exception {
        this.mockMvc.perform(get("/currency/denomination/3.5")).andDo(print()).andExpect(status().isBadRequest());
    }
}