package com.restapiform.util;

import com.restapiform.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Security 공통사용 Util
 */
@Component
public class SecurityCommonUtil {
    /**
     * 로그인 회원 권한체크
     * 현재 로그인 회원과 요청
     * @param id 접근 요청 정보
     */
    public void checkAccountAuthentication(Long id) throws Exception {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(account.getId())) {
            throw new Exception("접근 권한이 없는 계정입니다.");
        }
    }
}
