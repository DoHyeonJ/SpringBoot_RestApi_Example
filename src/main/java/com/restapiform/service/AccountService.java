package com.restapiform.service;

import com.restapiform.model.*;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthTokenRepository authTokenRepository;
    @Value("${spring.mail.username}")
    private String ccMail;

    public Account addAccount(Account account) {

        // email 중복체크
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email that already exists.");
        }

        account.setRole(Role.NOT_PERMITTED);
        // password encode
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        AuthToken authToken = new AuthToken();

        authToken.setToken(UUID.randomUUID().toString());
        authToken.setAccount(account);
        authToken.setType(TokenType.ACCOUNT_AUTH);
        authTokenRepository.save(authToken);

        Email email = new Email();
        // TODO : 서비스명 title 접두어에 달아주기, email 셋팅 메서드화 시켜주기 common으로
        email.setTitle("인증메일 입니다.");
        email.setAddress(account.getEmail());
        email.setCcAddress(ccMail);
        email.setTemplate("confirm_email");

        HashMap<String, String> emailValues = new HashMap<>();

        emailValues.put("name", account.getName() + " 님");
        // TODO : 인증할 url 넣어주기
        emailValues.put("url", "www.cafe24.com");
        emailService.sendTemplateMessage(email, emailValues);

        return accountRepository.save(account);
    }
}
