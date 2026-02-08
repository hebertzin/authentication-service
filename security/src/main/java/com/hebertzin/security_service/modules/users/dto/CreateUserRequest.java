package com.hebertzin.security_service.modules.users.dto;

public record CreateUserRequest(String name, String email, String password) {
}
