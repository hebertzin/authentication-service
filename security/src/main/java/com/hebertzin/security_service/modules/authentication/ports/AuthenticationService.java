package com.hebertzin.security_service.modules.authentication.ports;

import com.hebertzin.security_service.modules.authentication.AuthenticationRequest;

public interface AuthenticationService {
    String authenticate(AuthenticationRequest authenticationRequest);
}
