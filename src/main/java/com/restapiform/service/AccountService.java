package com.restapiform.service;

import com.restapiform.config.JwtTokenProvider;
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

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthTokenRepository authTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
        String token = emailService.sendTokenMail(account);

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

    /**
     * 로그인 Jwt 토큰 발급
     * @param account 로그인 시도 정보
     * @return jwt token
     */
    public String getJwtToken(Map<String, String> account) {
        // TODO 예외처리 controller 단에서 가능하게 리팩토링 필요
        Account loginAccount = accountRepository.findByEmail(account.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("잘못된 계정정보 입니다."));
        if (!passwordEncoder.matches(account.get("password"), loginAccount.getPassword())) {
            throw new IllegalArgumentException("잘못된 패스워드 입니다.");
        }
        if (loginAccount.getRole().equals(Role.NOT_PERMITTED)) {
            return "메일 인증이 되지않은 계정입니다. 메일인증을 진행해주시기 바랍니다.";
        }
        return jwtTokenProvider.createToken(loginAccount.getUsername(), loginAccount.getRole());
    }
}
