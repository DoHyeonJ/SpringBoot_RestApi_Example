package com.restapiform.service;

import com.restapiform.model.Account;
import com.restapiform.model.Follow;
import com.restapiform.model.FollowStatus;
import com.restapiform.repository.AccountRepository;
import com.restapiform.repository.FollowRepository;
import com.restapiform.util.SecurityCommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;
    private final SecurityCommonUtil securityCommonUtil;

    /**
     * 팔로우 목록 조회
     */
    public Follow getFollowList(Account account) {
        return followRepository.findByAccount(account);
    }

    /**
     * 팔료우 요청
     */
    public Follow requestFollow(Long id) throws Exception {
        Follow follow = new Follow();
        Account account = accountRepository.findById(id).orElseThrow(() -> new Exception("존재하지않는 회원입니다."));
        Account follower = securityCommonUtil.getLoginAccountInfo();

        // 이미 팔로우 요청한적 있는지 여부 체크
        if (followRepository.findByIdAndAccount(follow.getId(), account).isEmpty()) {
            follow.setAccount(account);
            follow.setFollowerId(follower.getId());
            follow.setFollowStatus(FollowStatus.REQUEST);
            followRepository.save(follow);
        } else {
            throw new Exception("이미 팔로우 신청한 계정입니다.");
        }

        return follow;
    }
}
