package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.UserService;
import com.hebertzin.security_service.exceptions.ConflictException;
import com.hebertzin.security_service.presentation.CreateUserRequest;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(CreateUserRequest request) {
        User user = new User(request.name(), request.email(), request.password());

        boolean existentUser = this.repo.existsByEmail(user.getEmail());

        if (existentUser) {
          throw  new ConflictException("User already exist");
        };

         user.setPassword(passwordEncoder.encode(user.getPassword()));

         return this.repo.save(user);
    }


    public Optional<User> findById(UUID userId) {
       return this.repo.findById(userId);
    }
}
