package com.hebertzin.security_service.modules.authentication.service;
import com.hebertzin.security_service.modules.authentication.dto.TokenResponse;
import com.hebertzin.security_service.modules.authentication.ports.AuthenticationService;
import com.hebertzin.security_service.modules.devices.ports.DeviceService;
import com.hebertzin.security_service.modules.login_attempts.ports.LoginAttemptService;
import com.hebertzin.security_service.infra.tokens.ports.TokenProvider;
import com.hebertzin.security_service.exceptions.ForbiddenException;
import com.hebertzin.security_service.exceptions.InvalidCredentialException;
import com.hebertzin.security_service.exceptions.NotFoundException;
import com.hebertzin.security_service.modules.authentication.dto.AuthenticationRequest;
import com.hebertzin.security_service.modules.login_attempts.dto.LoginAttemptRequest;
import com.hebertzin.security_service.modules.login_attempts.ports.LoginResult;
import com.hebertzin.security_service.modules.devices.ports.TrustLevelDevice;
import com.hebertzin.security_service.modules.users.repository.UserRepository;
import com.hebertzin.security_service.modules.devices.repository.entity.Device;
import com.hebertzin.security_service.modules.users.repository.entity.User;
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

    public TokenResponse authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> user = this.userRepository.findByEmail(authenticationRequest.email());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        boolean isValidCredential = this.isValidCredentials(authenticationRequest.password(), user.get().getPassword());

        if (!isValidCredential) {
            LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                            .email(authenticationRequest.email())
                            .userId(user.get().getId())
                            .result(LoginResult.FAILURE)
                            .build();

            this.loginAttempt.registerLoginAttempt(loginAttempt);
            throw  new InvalidCredentialException("Credential are invalids");
        }

       Device device = this.deviceService.createOrFindDevice(user.get().getId(),
                authenticationRequest.deviceType(),
                authenticationRequest.platform(),
                authenticationRequest.userAgent(),
                authenticationRequest.ip()
        );

        if (device.getTrustLevel() == TrustLevelDevice.UNTRUSTED) {
            LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                            .email(authenticationRequest.email())
                            .userId(user.get().getId())
                            .deviceId(null)
                            .result(LoginResult.FAILURE)
                            .build();

             this.loginAttempt.registerLoginAttempt(loginAttempt);
             throw  new ForbiddenException("OTP required");
        }

        LoginAttemptRequest loginAttempt = LoginAttemptRequest.builder()
                        .email(authenticationRequest.email())
                        .userId(user.get().getId())
                        .deviceId(device.getId())
                        .result(LoginResult.SUCCESS)
                        .build();
         this.loginAttempt.registerLoginAttempt(loginAttempt);
         return this.tokenProvider.generateToken(user.get().getEmail());
     }

    public boolean isValidCredentials(String rawPassword, String hash) {
        return passwordEncoder.matches(rawPassword, hash);
    }
}
