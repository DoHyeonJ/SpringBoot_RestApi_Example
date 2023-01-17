package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.AuthToken;
import com.restapiform.model.Role;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthTokenRepository authTokenRepository;

    /**
     * 신규 회원 추가
     * @param account 회원정보
     * @return 신규저장된 회원정보
     */
    public Account addAccount(Account account) {

        // email 중복체크
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email that already exists.");
        }

        // 일반 인증 전 회원 Role 설정
        account.setRole(Role.NOT_PERMITTED);
        // password encode
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        // 메일 인증 토큰 전송
        emailService.sendTokenMail(account);

        return accountRepository.save(account);
    }

    /**
     * 회원 Role(권한) 수정
     * @param authToken 인증토큰
     * @param role 권한 Enum
     * @return Http 상태코드 + 토큰정보
     */
    public ResponseEntity<Optional<AuthToken>> updateAccountRole(Optional<AuthToken> authToken, Role role) {

        if (authToken.isPresent()) {
            AuthToken selectAuthToken = authToken.get();
            Optional<Account> account = accountRepository.findById(selectAuthToken.getId());
            account.ifPresent(accountRole -> accountRole.setRole(role));

            authTokenRepository.deleteById(selectAuthToken.getId()); // 인증 완료된 토큰 삭제
            return new ResponseEntity<>(authToken, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
