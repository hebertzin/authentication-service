package com.hebertzin.security_service.modules.authentication.ports;

import com.hebertzin.security_service.modules.authentication.dto.AuthenticationRequest;
import com.hebertzin.security_service.modules.authentication.dto.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(AuthenticationRequest authenticationRequest);
    boolean isValidCredentials(String rawPassword, String hash);
}
