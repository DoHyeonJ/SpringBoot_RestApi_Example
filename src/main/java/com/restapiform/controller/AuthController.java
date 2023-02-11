package com.restapiform.controller;

import com.restapiform.model.AuthToken;
import com.restapiform.model.Role;
import com.restapiform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    /**
     * 회원가입 메일인증
     * @param token 인증토큰
     * @return 인증 회원정보
     */
    @GetMapping("/email/{token}")
    public ResponseEntity<AuthToken> emailAuthCheck(@PathVariable(value = "token") String token) throws Exception {
        return accountService.updateAccountRole(token, Role.USER);
    }
}
