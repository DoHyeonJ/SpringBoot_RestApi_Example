package com.restapiform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapiform.MockMvcTest;
import com.restapiform.model.Account;
import com.restapiform.model.Role;
import com.restapiform.repository.AccountRepository;
import com.restapiform.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    private String token;



    // TODO : ... 공통으로 사용되게 빼야됨 지저분함
    @BeforeEach // 초기화
    void initAccount() throws Exception {
        // 회원가입
        Map<String, String> input = new HashMap<>();
        input.put("email", "wyehgus@naver.com");
        input.put("password", "1234567890");

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));

        // 이메일 인증
        Account account = accountRepository.findById((long)1).get();
        account.setRole(Role.USER);
        accountRepository.save(account);

        // JWT 토큰 발급
        token = accountService.getJwtToken(input);
    }

    @DisplayName("프로필 조회 성공")
    @Test
    void getProfile() throws Exception {
        // given

        // when
        mockMvc.perform(get("/profile/1").header("X-AUTH-TOKEN", token))

        // then
        .andExpect(status().isOk())
        .andDo(print());
    }

    @DisplayName("프로필 수정 성공")
    @Test
    void putProfile() throws Exception {
        // given
        Map<String, String> input = new HashMap<>();
        input.put("name", "test");
        input.put("birth", "19950803");
        input.put("description", "test account");
        input.put("gender", "man");
        input.put("phone", "01085506941");

        // when
        mockMvc.perform(put("/profile/1")
                        .header("X-AUTH-TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))

        // then
        .andExpect(status().isOk())
        .andDo(print());
    }
}