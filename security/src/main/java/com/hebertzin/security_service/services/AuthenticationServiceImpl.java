package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.AuthenticationService;
import com.hebertzin.security_service.domain.DeviceService;
import com.hebertzin.security_service.domain.LoginAttemptService;
import com.hebertzin.security_service.domain.TokenProvider;
import com.hebertzin.security_service.exceptions.ForbiddenException;
import com.hebertzin.security_service.exceptions.InvalidCredentialException;
import com.hebertzin.security_service.exceptions.NotFoundException;
import com.hebertzin.security_service.presentation.AuthenticationRequest;
import com.hebertzin.security_service.presentation.LoginAttemptRequest;
import com.hebertzin.security_service.presentation.LoginResult;
import com.hebertzin.security_service.presentation.TrustLevel;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.Device;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private  final DeviceService deviceService;
    private  final LoginAttemptService loginAttempt;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider,
            DeviceService deviceService,
            LoginAttemptService loginAttempt
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.deviceService = deviceService;
        this.loginAttempt = loginAttempt;
    }

    public String authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> user = this.userRepository.findByEmail(authenticationRequest.email());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        boolean isCorrectCredentials = this.passwordEncoder.matches(authenticationRequest.password(), user.get().getPassword());

        if (!isCorrectCredentials) {
            LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                            .email(authenticationRequest.email())
                            .userId(user.get().getId())
                            .result(LoginResult.FAILURE)
                            .ip(authenticationRequest.ip())
                            .build();

            this.loginAttempt.registerLoginAttempt(loginAttempt);
            throw  new InvalidCredentialException("Credential are invalids");
        }

       Device device =  this.deviceService.createOrFindDevice(user.get().getId(),
                authenticationRequest.deviceType(),
                authenticationRequest.platform(),
                authenticationRequest.userAgent(),
                authenticationRequest.ip()
        );

        if (device.getTrustLevel() == TrustLevel.UNTRUSTED) {
            LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                            .email(authenticationRequest.email())
                            .userId(user.get().getId())
                            .deviceId(null)
                            .result(LoginResult.FAILURE)
                            .ip(null)
                            .build();

             this.loginAttempt.registerLoginAttempt(loginAttempt);
             throw  new ForbiddenException("OTP required");
        }

        LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                        .email(authenticationRequest.email())
                        .userId(user.get().getId())
                        .deviceId(device.getId())
                        .result(LoginResult.SUCCESS)
                        .ip(device.getIpLast())
                        .build();

         this.loginAttempt.registerLoginAttempt(loginAttempt);

         return this.tokenProvider.generateToken(user.get().getEmail());
     }

}
