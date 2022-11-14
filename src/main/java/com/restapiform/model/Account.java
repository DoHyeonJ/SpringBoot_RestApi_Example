package com.restapiform.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email; // 사용자 이메일

    @Column(nullable = false)
    private String name; // 사용자 명

    @Column(nullable = false)
    private String birth; // 사용자 생년월일 8자리 ex) 19950803

    @Column
    private String isEmailCheck; // 인증완료 : T, 미완료 : F

}
