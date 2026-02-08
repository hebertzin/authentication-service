package com.hebertzin.security_service.services;

import com.hebertzin.security_service.domain.LoginAttemptService;
import com.hebertzin.security_service.presentation.RegisterLoginAttemptRequest;
import com.hebertzin.security_service.repository.LoginAttemptRepository;
import com.hebertzin.security_service.repository.entities.LoginAttempt;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
    private final LoginAttemptRepository repo;

    public  LoginAttemptServiceImpl(LoginAttemptRepository repo) {
        this.repo = repo;
    }

    public LoginAttempt registerLoginAttempt(RegisterLoginAttemptRequest loginAttemptRequest) {
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
