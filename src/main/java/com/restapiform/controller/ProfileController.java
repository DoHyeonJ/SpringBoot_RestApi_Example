package com.restapiform.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping
    public String getProfile() {
        // TODO JWT 로그인 토큰 권한 체크용
        return "PROFILE TEST";
    }

}
