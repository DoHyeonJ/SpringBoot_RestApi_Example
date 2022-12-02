package com.restapiform.Account;

import com.restapiform.model.Account;
import com.restapiform.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    /**
     * 신규 유저 생성
     * @return Account
     */
    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestBody @Valid Account account) {
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }
}
