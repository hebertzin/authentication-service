package com.hebertzin.security_service.domain;
import com.hebertzin.security_service.presentation.CreateUserRequest;
import com.hebertzin.security_service.repository.entities.User;

public interface UserService {
    User save(CreateUserRequest request);
}
