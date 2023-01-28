package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.Profile;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    /**
     * 프로필 조회
     * @param accountId 회원 고유번호
     * @return 프로필 정보
     * @throws Exception ex
     */
    public Profile getProfile(Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception("존재하지않는 회원입니다."));
        return profileRepository.findByAccount(account).orElseThrow(() -> new Exception("존재하지않는 프로필입니다."));
    }
}
