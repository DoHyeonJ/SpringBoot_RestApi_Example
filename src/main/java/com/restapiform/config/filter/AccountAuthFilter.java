package com.restapiform.config.filter;

import com.restapiform.util.SecurityCommonUtil;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 회원 수정 권한 인증 필터
 */
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/profile/*")
public class AccountAuthFilter implements Filter {

    private final SecurityCommonUtil securityCommonUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String[] uriParameter = ((HttpServletRequest) request).getRequestURI().split("/");
        // 조회를 제외한 항목들 필터처리
        if (!((HttpServletRequest) request).getMethod().equals("GET")) {
            try {
                securityCommonUtil.checkAccountAuthentication(Long.valueOf(uriParameter[uriParameter.length-1]));
            } catch (Exception e) {
                throw new RuntimeException("수정 권한이 없습니다.");
            }
        }
        chain.doFilter(request, response);
    }
}
