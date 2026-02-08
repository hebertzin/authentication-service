package com.hebertzin.security_service.modules.login_attempts.service;

import com.hebertzin.security_service.modules.login_attempts.ports.LoginAttemptService;
import com.hebertzin.security_service.modules.login_attempts.dto.LoginAttemptRequest;
import com.hebertzin.security_service.modules.login_attempts.repository.LoginAttemptRepository;
import com.hebertzin.security_service.modules.login_attempts.repository.entity.LoginAttempt;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private final LoginAttemptRepository repo;

    public  LoginAttemptServiceImpl(LoginAttemptRepository repo) {
        this.repo = repo;
    }

    public LoginAttempt registerLoginAttempt(LoginAttemptRequest loginAttemptRequest) {
       LoginAttempt login =  new LoginAttempt(
               loginAttemptRequest.email(),
               loginAttemptRequest.userId(),
               loginAttemptRequest.deviceId(),
               loginAttemptRequest.result()
        );

       return this.repo.save(login);
    }
}
