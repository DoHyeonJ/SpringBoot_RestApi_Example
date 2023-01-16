package com.restapiform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // rest api 는 서버에 인증정보 보관하지않아서 csrf 를 비활성화 해도 무관함 (세션대신 토큰을 사용함)
        http.csrf().disable();
        http.authorizeHttpRequests().antMatchers("/account").permitAll();
        return http.build();
    }
}