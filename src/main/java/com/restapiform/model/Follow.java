package com.restapiform.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long followerId;

    @Enumerated(EnumType.STRING)
    private FollowStatus followStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id")
    private Account account;
}
