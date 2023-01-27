package com.restapiform.repository;

import com.restapiform.model.Account;
import com.restapiform.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Override
    Optional<Profile> findById(Long aLong);
    Optional<Profile> findByAccount(Account id);
}
