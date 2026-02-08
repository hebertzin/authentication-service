package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.AuthenticationService;
import com.hebertzin.security_service.domain.DeviceService;
import com.hebertzin.security_service.domain.TokenProvider;
import com.hebertzin.security_service.exceptions.ForbiddenException;
import com.hebertzin.security_service.exceptions.InvalidCredentialException;
import com.hebertzin.security_service.exceptions.NotFoundException;
import com.hebertzin.security_service.presentation.AuthenticationRequest;
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

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     TokenProvider tokenProvider,
                                     DeviceService deviceService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.deviceService = deviceService;
    }

    public String authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> user = this.userRepository.findByEmail(authenticationRequest.email());

        boolean existentUser = this.userRepository.existsByEmail(authenticationRequest.email());

        if (!existentUser) {
            throw new NotFoundException("User not found");
        }

        boolean isCorrectCredentials = this.passwordEncoder.matches(authenticationRequest.password(), user.get().getPassword());

        if (!isCorrectCredentials) {
            throw  new InvalidCredentialException("Credential are invalids");
        }

       Device device =  this.deviceService.createOrFindDevice(user.get().getId(),
                authenticationRequest.deviceType(),
                authenticationRequest.platform(),
                authenticationRequest.userAgent(),
                authenticationRequest.ip()
        );

        if (device.getTrustLevel() == TrustLevel.UNTRUSTED) {
             throw  new ForbiddenException("OTP required");
        }

         return this.tokenProvider.generateToken(user.get().getEmail());
     }

}
