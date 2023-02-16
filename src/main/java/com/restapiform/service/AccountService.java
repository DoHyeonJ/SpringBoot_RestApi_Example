package com.restapiform.service;

import com.restapiform.config.JwtTokenProvider;
import com.restapiform.model.Account;
import com.restapiform.model.AuthToken;
import com.restapiform.model.Profile;
import com.restapiform.model.Role;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.AuthTokenRepository;
import com.restapiform.repository.ProfileRepository;
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
    private final ProfileRepository profileRepository;
    private final AuthService authService;

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

        Account newAccount = accountRepository.save(account);

        // 신규 회원 추가시 프로필도 같이 추가시켜줌
        Profile newProfile = new Profile();
        newProfile.setAccount(newAccount);
        profileRepository.save(newProfile);

        return newAccount;
    }

    /**
     * 회원 Role(권한) 수정
     * @param token 인증토큰
     * @param role 권한 Enum
     * @return Http 상태코드 + 토큰정보
     */
    public ResponseEntity<AuthToken> updateAccountRole(String token, Role role) throws Exception {
        AuthToken authToken = authService.emailTokenCheck(token).orElseThrow(() -> new Exception("잘못된 인증 토큰입니다."));
        Account account = accountRepository.findById(authToken.getId()).orElseThrow(() -> new Exception("잘못된 인증 토큰입니다."));
        account.setRole(role);
        authTokenRepository.deleteById(authToken.getId()); // 인증 완료된 토큰 삭제
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }

    /**
     * 로그인 Jwt 토큰 발급
     * @param account 로그인 시도 정보
     * @return jwt token
     */
    public String getJwtToken(Map<String, String> account) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(account.get("email"));
        if (optionalAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 계정정보 입니다.");
        }
        Account loginAccount = optionalAccount.get();
        if (!passwordEncoder.matches(account.get("password"), loginAccount.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 패스워드 입니다.");
        }
        if (loginAccount.getRole().equals(Role.NOT_PERMITTED)) {
            return "메일 인증이 되지않은 계정입니다. 메일인증을 진행해주시기 바랍니다.";
        }
        return jwtTokenProvider.createToken(loginAccount.getUsername(), loginAccount.getRole());
    }
}
