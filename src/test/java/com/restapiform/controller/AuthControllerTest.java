package com.restapiform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiform.MockMvcTest;
import com.restapiform.model.Account;
import com.restapiform.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmailService emailService;

    @DisplayName("이메일 토큰 인증 성공")
    @Test
    void emailTokenCheck() throws Exception {
        // given
        Map<String, String> input = new HashMap<>();
        Account account = new Account();
        account.setEmail("jdh7693@naver.com");
        account.setPassword("1234");
        account.setName("test");
        account.setBirth("19950803");
        String token = emailService.makeTokenAndSave(account);

        // when
        mockMvc.perform(get("/auth/signup/" + token))

        // then
        .andExpect(status().isOk())
        .andDo(print());
    }
}