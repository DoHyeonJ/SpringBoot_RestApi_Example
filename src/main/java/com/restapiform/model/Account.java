package com.restapiform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @NotEmpty(message = "id is required parameter")
    @Column(nullable = false)
    private String id;

    @NotEmpty(message = "password is required parameter")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "email is required parameter")
    @Column(nullable = false)
    private String email; // 사용자 이메일

    @NotEmpty(message = "name is required parameter")
    @Column(nullable = false)
    private String name; // 사용자 명

    @NotEmpty(message = "birth is required parameter")
    @Column(nullable = false)
    private String birth; // 사용자 생년월일 8자리 ex) 19950803

    @Column
    private String isEmailCheck; // 인증완료 : T, 미완료 : F

    @Enumerated(EnumType.STRING)
    private Role role; // 기본회원

}
