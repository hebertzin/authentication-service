package com.hebertzin.security_service.exceptions;

public class ForbiddenException extends RuntimeException {
    public  ForbiddenException(String message) {
        super(message);
    }
}
