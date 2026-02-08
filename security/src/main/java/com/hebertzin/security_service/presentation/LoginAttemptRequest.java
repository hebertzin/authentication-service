package com.hebertzin.security_service.presentation;

import java.util.UUID;

public record LoginAttemptRequest(
        String email,
        UUID userId,
        UUID deviceId,
        LoginResult result,
        String ip
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String email;
        private UUID userId;
        private UUID deviceId;
        private LoginResult result;
        private String ip;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder deviceId(UUID deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder result(LoginResult result) {
            this.result = result;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public LoginAttemptRequest build() {
            return new LoginAttemptRequest(email, userId, deviceId, result, ip);
        }
    }
}
