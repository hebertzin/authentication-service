package com.hebertzin.security_service.modules.users.ports;
import com.hebertzin.security_service.modules.users.dto.CreateUserRequest;
import com.hebertzin.security_service.modules.users.repository.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(CreateUserRequest request);
    Optional<User> findById(UUID userId);
}
