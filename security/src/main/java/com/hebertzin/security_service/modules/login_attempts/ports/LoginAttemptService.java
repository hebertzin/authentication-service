package com.hebertzin.security_service.modules.login_attempts.ports;

import com.hebertzin.security_service.modules.login_attempts.dto.LoginAttemptRequest;
import com.hebertzin.security_service.modules.login_attempts.repository.entity.LoginAttempt;

public interface LoginAttemptService {
    LoginAttempt registerLoginAttempt(LoginAttemptRequest loginAttemptRequest);
}
