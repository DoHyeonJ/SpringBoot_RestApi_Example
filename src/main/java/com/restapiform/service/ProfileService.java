package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.Profile;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    public Profile getProfile(Long accountId) {
        // todo service단에 존재여부 체크하는걸로 공통함수 만들어주기
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            Optional<Profile> profile = profileRepository.findByAccount(account.get());
            if (profile.isPresent()) {
                return profile.get();
            } else {
                throw new IllegalArgumentException("존재하지 않는 프로필 입니다.");
            }
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원 입니다.");
        }
    }
}
