package com.restapiform.controller;

import com.restapiform.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@MockMvcTest
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("프로필 조회")
    @Test
    void getProfile() {

    }
}