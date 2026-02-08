package com.hebertzin.security_service.modules.attempts.repository;
import com.hebertzin.security_service.modules.attempts.repository.entity.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, UUID> {
}