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

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id, @RequestHeader Map<String, String> head) throws Exception {
        // TODO 프로필 봐도 되는 회원인지 체크해주는 과정 필요
        // System.out.println(head.get("x-auth-token"));
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
