package com.restapiform.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),
    NOT_PERMITTED("ROLE_NOT_PERMITTED", "메일 인증전");
    private final String key;
    private final String title;
}
