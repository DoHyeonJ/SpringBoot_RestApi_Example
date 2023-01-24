package com.restapiform.repository;

import com.restapiform.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(@NotBlank String email);

    Optional<Account> findByEmail(String email);
}
