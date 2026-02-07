package com.hebertzin.security_service.domain;

public interface TokenProvider {
    String generateToken(String subject);
}
