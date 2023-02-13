package com.restapiform.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FollowStatus {
    REQUEST("REQUEST", "요청"),
    ACCEPT("ACCEPT", "허용"),
    REJECTED("REJECTED", "거절"),
    BLOCKED("BLOCKED", "차단");
    private final String key;
    private final String status;
}
