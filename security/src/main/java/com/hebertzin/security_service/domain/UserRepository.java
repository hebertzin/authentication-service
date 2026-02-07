package com.hebertzin.security_service.domain;

import com.hebertzin.security_service.repository.entities.User;

import java.util.Optional;

public interface UserRepository {
    public Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
