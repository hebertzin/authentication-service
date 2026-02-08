package com.hebertzin.security_service.infra.tokens.ports;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

public interface TokenProvider {
    String generateToken(String subject);
    String extracSubject(String token);
    Jws<Claims> parseAndValidate(String token) throws JwtException;
}
