package com.hebertzin.security_service.modules.login_attempts.repository.entity;

import com.hebertzin.security_service.modules.login_attempts.ports.LoginResult;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "login_attempts")
public class LoginAttempt {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "device_id")
    private UUID deviceId;

    @Column(nullable = false, length = 20)
    private LoginResult result;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected LoginAttempt() {
    }

    public LoginAttempt(
            String email,
            UUID userId,
            UUID deviceId,
            LoginResult result
    ) {
        this.email = email;
        this.userId = userId;
        this.deviceId = deviceId;
        this.result = result;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
