package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.Role;
import com.restapiform.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account addAccount(Account account) {

        // id, email 중복체크
        if (accountRepository.existsById(account.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID that already exists.");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email that already exists.");
        }

        account.setRole(Role.USER);

        // password encode
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
}
