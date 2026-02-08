package com.hebertzin.security_service.domain;
import com.hebertzin.security_service.presentation.CreateUserRequest;
import com.hebertzin.security_service.repository.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(CreateUserRequest request);
    Optional<User> findById(UUID userId);
}
