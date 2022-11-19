package com.restapiform.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {
    ACCOUNT_AUTH("TYPE_ACCOUNT_AUTH", "회원 메일인증");
    private final String key;
    private final String title;
}
