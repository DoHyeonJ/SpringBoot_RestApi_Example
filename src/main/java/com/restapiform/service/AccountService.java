package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account addAccount(Account account) {
        // TODO : 필수변수 체크
        // TODO : 중복체크
        return accountRepository.save(account);
    }
}
