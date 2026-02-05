package com.hebertzin.security_service.services;

import com.hebertzin.security_service.exceptions.UserAlreadyExist;
import com.hebertzin.security_service.presentation.CreateUserRequest;
import com.hebertzin.security_service.repository.UserRepository;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEnconder;

    public UserService(UserRepository repo, PasswordEncoder passwordEnconder) {
        this.repo = repo;
        this.passwordEnconder = passwordEnconder;
    }

    public  User save(CreateUserRequest request) {
        User user = new User(request.name(), request.email(), request.password());

        boolean existentUser = this.repo.existsByEmail(user.getEmail());

        if (existentUser) {
          throw  new UserAlreadyExist("User Already exist");
        };

         user.setPassword(passwordEnconder.encode(user.getPassword()));

         return this.repo.save(user);
    }
}
