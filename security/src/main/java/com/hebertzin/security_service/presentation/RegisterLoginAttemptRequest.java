package com.hebertzin.security_service.presentation;

import java.util.UUID;

public record RegisterLoginAttemptRequest(
        String email,
        UUID userId,
        UUID deviceId,
        String result,
        String ip
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String email;
        private UUID userId;
        private UUID deviceId;
        private String result;
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

        public Builder result(String result) {
            this.result = result;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public RegisterLoginAttemptRequest build() {
            return new RegisterLoginAttemptRequest(email, userId, deviceId, result, ip);
        }
    }
}
