package com.hebertzin.security_service.presentation;

public record AuthenticationRequest(
        String email,
        String password,
        String deviceType,
        String platform,
        String userAgent,
        String ip
) {
}
