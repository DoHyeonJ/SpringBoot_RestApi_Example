package com.restapiform.controller;

import com.restapiform.model.AuthToken;
import com.restapiform.model.Role;
import com.restapiform.service.AccountService;
import com.restapiform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @GetMapping("/signup/{token}")
    public ResponseEntity<Optional<AuthToken>> emailAuthCheck(@PathVariable(value = "token") String token) {
        Optional<AuthToken> authToken = authService.emailTokenCheck(token);
        return accountService.updateAccountRole(authToken, Role.USER);
    }
}
