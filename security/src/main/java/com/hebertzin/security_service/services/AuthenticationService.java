package com.hebertzin.security_service.services;
import com.hebertzin.security_service.exceptions.InvalidCredentials;
import com.hebertzin.security_service.exceptions.NotFoundException;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void authenticate(String email, String password) {
        Optional<User> user = this.userRepository.findByEmail(email);

        boolean existentUser = this.userRepository.existsByEmail(email);

        if (!existentUser) {
            throw new NotFoundException("User not found");
        }

        boolean isCorrectCredentials = this.passwordEncoder.matches(password, user.get().getPassword());

        if (!isCorrectCredentials) {
            throw  new InvalidCredentials("Credential are invalids");
        }

     }

}
