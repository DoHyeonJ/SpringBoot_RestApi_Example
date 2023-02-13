package com.restapiform.repository;

import com.restapiform.model.Account;
import com.restapiform.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByAccount(Account account);
    Optional<Follow> findByIdAndAccount(Long id, Account account);
}
