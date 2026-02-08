package com.hebertzin.security_service.repository;
import com.hebertzin.security_service.repository.entities.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, UUID> {
}