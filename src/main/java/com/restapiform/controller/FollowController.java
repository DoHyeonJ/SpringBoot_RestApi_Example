package com.restapiform.controller;

import com.restapiform.model.Account;
import com.restapiform.model.Follow;
import com.restapiform.service.AccountService;
import com.restapiform.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    /**
     * 팔로우 목록 조회
     */
    @GetMapping
    public ResponseEntity<Follow> getFollowList() {
        Account account = accountService.getLoginAccountInfo();
        return new ResponseEntity<>(followService.getFollowList(account), HttpStatus.OK);
    }

    /**
     * 팔로우 요청
     */
    @PostMapping("/{id}")
    public ResponseEntity<Follow> requestFollow(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(followService.requestFollow(id), HttpStatus.CREATED);
    }

    /**
     * 팔로우 승인, 거절, 차단
     */
    @PutMapping
    public String updateFollow() {
        // TODO 팔로우 요청 승인, 거절
        return "FOLLOW TEST";
    }

    @DeleteMapping
    public String deleteProfile() {
        // TODO 팔로우 삭제
        return "FOLLOW TEST";
    }
}
