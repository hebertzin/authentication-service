package com.hebertzin.security_service.modules.attempts.ports;

import com.hebertzin.security_service.modules.attempts.dto.LoginAttemptRequest;
import com.hebertzin.security_service.modules.attempts.repository.entity.LoginAttempt;

public interface LoginAttemptService {
    LoginAttempt registerLoginAttempt(LoginAttemptRequest loginAttemptRequest);
}
