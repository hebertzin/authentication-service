package com.hebertzin.security_service.repository;

import com.hebertzin.security_service.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);
}
