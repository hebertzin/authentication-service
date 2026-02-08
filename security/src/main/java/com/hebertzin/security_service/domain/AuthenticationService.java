package com.hebertzin.security_service.domain;

import com.hebertzin.security_service.presentation.AuthenticationRequest;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest authenticationRequest);
}
