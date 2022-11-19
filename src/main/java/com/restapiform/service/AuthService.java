package com.restapiform.service;

import com.restapiform.model.AuthToken;
import com.restapiform.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenRepository authTokenRepository;

    public Optional<AuthToken> emailTokenCheck(String token) {
        return authTokenRepository.findIdByToken(token);
    }
}
