package com.restapiform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "email is required parameter")
    @Column(nullable = false, unique = true)
    private String email; // 사용자 이메일

    @NotEmpty(message = "password is required parameter")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "name is required parameter")
    @Column(nullable = false)
    private String name; // 사용자 명

    @NotEmpty(message = "birth is required parameter")
    @Column(nullable = false)
    private String birth; // 사용자 생년월일 8자리 ex) 19950803

    @Enumerated(EnumType.STRING)
    private Role role; // 기본회원

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
