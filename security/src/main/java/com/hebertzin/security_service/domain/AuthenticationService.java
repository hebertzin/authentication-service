package com.hebertzin.security_service.domain;

public interface AuthenticationService {
    String authenticate(String email, String password);
}
