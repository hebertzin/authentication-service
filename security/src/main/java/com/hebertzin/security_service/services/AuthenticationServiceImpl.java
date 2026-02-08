package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.AuthenticationService;
import com.hebertzin.security_service.domain.TokenProvider;
import com.hebertzin.security_service.exceptions.InvalidCredentialException;
import com.hebertzin.security_service.exceptions.NotFoundException;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public String authenticate(String email, String password) {
        Optional<User> user = this.userRepository.findByEmail(email);

        boolean existentUser = this.userRepository.existsByEmail(email);

        if (!existentUser) {
            throw new NotFoundException("User not found");
        }

        boolean isCorrectCredentials = this.passwordEncoder.matches(password, user.get().getPassword());

        if (!isCorrectCredentials) {
            throw  new InvalidCredentialException("Credential are invalids");
        }

         return this.tokenProvider.generateToken(user.get().getEmail());
     }

}
