package com.restapiform.controller;

import com.restapiform.model.Profile;
import com.restapiform.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 나의 프로필 조회
     */
    @GetMapping
    public ResponseEntity<Profile> getMyProfile() throws Exception {
        return new ResponseEntity<>(profileService.getMyProfile(), HttpStatus.OK);
    }

    /**
     * 프로필 조회
     * @param id 조회 계정 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
    }

    @PostMapping
    public String createProfile() {
        // TODO 프로필 생성
        return "PROFILE TEST";
    }

    /**
     * 프로필 수정
     * @param id 조회 계정 정보
     * @param data 프로필 수정 요청 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Map<String, String> data) throws Exception {
        return new ResponseEntity<>(profileService.updateProfile(id, data), HttpStatus.OK);
    }

    @DeleteMapping
    public String deleteProfile() {
        // TODO 프로필 삭제
        return "PROFILE TEST";
    }

}
