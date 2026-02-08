package com.hebertzin.security_service.domain;

import com.hebertzin.security_service.presentation.RegisterLoginAttemptRequest;
import com.hebertzin.security_service.repository.entities.LoginAttempt;

public interface LoginAttemptService {
    LoginAttempt registerLoginAttempt(RegisterLoginAttemptRequest loginAttemptRequest);
}
