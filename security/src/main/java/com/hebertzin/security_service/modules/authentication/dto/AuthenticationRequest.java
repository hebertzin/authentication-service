package com.hebertzin.security_service.modules.authentication.dto;

public record AuthenticationRequest(
        String email,
        String password,
        String deviceType,
        String platform,
        String userAgent,
        String ip
) {
}
