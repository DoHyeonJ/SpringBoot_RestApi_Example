package com.restapiform.repository;

import com.restapiform.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(@NotBlank String id);
    boolean existsByEmail(@NotBlank String email);
}
