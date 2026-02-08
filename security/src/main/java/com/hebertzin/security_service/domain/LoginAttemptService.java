package com.hebertzin.security_service.domain;

import com.hebertzin.security_service.presentation.LoginAttemptRequest;
import com.hebertzin.security_service.repository.entities.LoginAttempt;

public interface LoginAttemptService {
    LoginAttempt registerLoginAttempt(LoginAttemptRequest loginAttemptRequest);
}
