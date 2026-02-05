package com.hebertzin.security_service.presentation;

public record CreateUserRequest(String name, String email, String password) {
}
