package com.restapiform.controller;

import com.restapiform.model.Profile;
import com.restapiform.service.ProfileService;
import com.restapiform.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final SecurityUtil securityUtil;

    /**
     * 프로필 조회
     * @param id 조회 계정 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) throws Exception {
        securityUtil.checkAccountAuthentication(id); // 조회 권한 체크
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
    }

    @PostMapping
    public String createProfile() {
        // TODO 프로필 생성
        return "PROFILE TEST";
    }

    @PutMapping
    public String updateProfile() {
        // TODO 프로필 수정
        return "PROFILE TEST";
    }

    @DeleteMapping
    public String deleteProfile() {
        // TODO 프로필 삭제
        return "PROFILE TEST";
    }

}
