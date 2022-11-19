package com.restapiform.service;

import com.restapiform.config.ConstVariable;
import com.restapiform.model.*;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Optional;
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

        account.setRole(Role.NOT_PERMITTED);
        // password encode
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        AuthToken authToken = new AuthToken();
        String uuid = UUID.randomUUID().toString();

        authToken.setToken(uuid);
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
        emailValues.put("service_name", ConstVariable.SERVICE_NAME);
        emailValues.put("url", ConstVariable.MAIN_URL + "/auth/signup/" + uuid);
        emailService.sendTemplateMessage(email, emailValues);

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
            account.ifPresent(accountRole ->
                    accountRole.setRole(role));

            authTokenRepository.deleteById(selectAuthToken.getId()); // 인증 완료된 토큰 삭제
            return new ResponseEntity<>(authToken, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
