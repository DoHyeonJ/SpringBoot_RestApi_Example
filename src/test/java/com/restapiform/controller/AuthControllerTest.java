package com.restapiform.controller;

import com.restapiform.MockMvcTest;
import com.restapiform.config.ConstVariable;
import com.restapiform.model.Account;
import com.restapiform.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmailService emailService;

    @DisplayName("이메일 토큰 인증 성공")
    @Test
    void emailTokenCheck() throws Exception {
        // given
        Account account = new Account();
        account.setEmail(ConstVariable.TEST_EMAIL);
        account.setPassword("1234");
        String token = emailService.makeTokenAndSave(account);

        // when
        mockMvc.perform(get("/auth/email/" + token))

        // then
        .andExpect(status().isOk())
        .andDo(print());
    }

    @DisplayName("이메일 토큰 인증 실패 - 잘못된 토큰 인증")
    @Test
    void wrongTokenErrorToEmailTokenCheck() throws Exception {
        // given
        String token = "Wrong Token";

        // when
        mockMvc.perform(get("/auth/email/" + token))

                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}