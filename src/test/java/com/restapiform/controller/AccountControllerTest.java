package com.restapiform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiform.MockMvcTest;
import com.restapiform.config.ConstVariable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@MockMvcTest
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("신규 회원가입 성공")
    @Test
    void completeToSignUpAccount() throws Exception {
        // given
        Map<String, String> input = new HashMap<>();
        input.put("email", ConstVariable.TEST_EMAIL);
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

        // then
        .andExpect(status().isCreated())
        .andDo(print());
    }

    @DisplayName("신규 회원가입 실패 - 중복된 이메일")
    @Test
    void duplicateEmailErrorToSignUpAccount() throws Exception {
        // given
        completeToSignUpAccount();
        Map<String, String> input = new HashMap<>();
        input.put("email", ConstVariable.TEST_EMAIL);
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("신규 회원가입 실패 - 값이 비어있는 request body")
    @Test
    void requestBodyErrorToSignUpAccount() throws Exception {
        // given
        Map<String, String> input = new HashMap<>();
        input.put("email", "");
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원 로그인 성공")
    @Test
    void completeToAccountLogin() throws Exception {
        // given
        completeToSignUpAccount(); // 회원가입
        Map<String, String> input = new HashMap<>();
        input.put("email", ConstVariable.TEST_EMAIL);
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))

        // then
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원 로그인 실패 - 잘못된 계정정보")
    @Test
    void wrongAccountEmailErrorToAccountLogin() throws Exception {
        // given
        completeToSignUpAccount(); // 회원가입
        Map<String, String> input = new HashMap<>();
        input.put("email", "wrong" + ConstVariable.TEST_EMAIL);
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("회원 로그인 실패 - 값이 비어있는 request body")
    @Test
    void requestBodyErrorToAccountLogin() throws Exception {
        // given
        completeToSignUpAccount(); // 회원가입
        Map<String, String> input = new HashMap<>();
        input.put("email", "");
        input.put("password", "1234567890");

        // when
        mockMvc.perform(post("/accounts/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}