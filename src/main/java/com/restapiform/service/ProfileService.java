package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.Profile;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;

    /**
     * 프로필 조회
     * @param accountId 회원 고유번호
     */
    public Profile getProfile(Long accountId) throws Exception {
        return checkAccountAndProfile(accountId);
    }

    /**
     * 프로필 정보 수정
     * @param accountId 회원 고유번호
     * @param data 요청 데이터
     */
    public Profile updateProfile(Long accountId, Map<String, String> data) throws Exception {
        Profile profile = checkAccountAndProfile(accountId);
        profile.setName(data.get("name"));
        profile.setBirth(data.get("birth"));
        profile.setDescription(data.get("description"));
        profile.setGender(data.get("gender"));
        profile.setPhone(data.get("phone"));
        return profileRepository.save(profile);
    }

    /**
     * 회원 및 프로필 존재 유무 체크
     * @param accountId 회원고유번호
     */
    private Profile checkAccountAndProfile(Long accountId) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new Exception("존재하지않는 회원입니다."));
        return profileRepository.findByAccount(account).orElseThrow(() -> new Exception("존재하지않는 프로필입니다."));
    }
}
