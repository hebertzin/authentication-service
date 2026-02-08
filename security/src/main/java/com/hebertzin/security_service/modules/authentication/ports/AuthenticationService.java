package com.hebertzin.security_service.modules.authentication.ports;

import com.hebertzin.security_service.modules.authentication.dto.AuthenticationRequest;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest authenticationRequest);
    boolean isValidCredentials(String rawPassword, String hash);
}
