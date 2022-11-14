package com.restapiform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // rest api 는 서버에 인증정보 보관하지않아서 csrf 를 비활성화 해도 무관함
        http.authorizeHttpRequests().antMatchers("/account").permitAll();
        return http.build();
    }
}