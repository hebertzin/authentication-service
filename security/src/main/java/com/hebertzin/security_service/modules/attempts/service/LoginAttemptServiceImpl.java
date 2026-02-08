package com.hebertzin.security_service.modules.attempts.service;

import com.hebertzin.security_service.modules.attempts.ports.LoginAttemptService;
import com.hebertzin.security_service.modules.attempts.dto.LoginAttemptRequest;
import com.hebertzin.security_service.modules.attempts.repository.LoginAttemptRepository;
import com.hebertzin.security_service.modules.attempts.repository.entity.LoginAttempt;
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
               loginAttemptRequest.result(),
               loginAttemptRequest.ip()
        );

       return this.repo.save(login);
    }
}
