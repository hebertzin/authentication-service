package com.hebertzin.security_service.infra.tokens.ports;

import com.hebertzin.security_service.modules.authentication.dto.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

public interface TokenProvider {
    TokenResponse generateToken(String subject);
    String extracSubject(String token);
    Jws<Claims> parseAndValidate(String token) throws JwtException;
}
