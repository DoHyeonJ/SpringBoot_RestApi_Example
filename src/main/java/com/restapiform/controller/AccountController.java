package com.restapiform.controller;

import com.restapiform.config.JwtTokenProvider;
import com.restapiform.model.Account;
import com.restapiform.repository.AccountRepository;
import com.restapiform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 신규 유저 생성
     * @return Account
     */
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody @Valid Account account) {
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    // TODO 메일인증회원인지 체크 필요 service단으로 이동 필요
    @PostMapping("/login")
    public ResponseEntity<String> loginAccount(@RequestBody Map<String, String> account) {
        Account loginAccount = accountRepository.findByEmail(account.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(account.get("password"), loginAccount.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        String token = jwtTokenProvider.createToken(loginAccount.getUsername(), loginAccount.getRole());
        return new ResponseEntity<>(token, HttpStatus.OK); // 토큰 정보 리턴
    }
}
