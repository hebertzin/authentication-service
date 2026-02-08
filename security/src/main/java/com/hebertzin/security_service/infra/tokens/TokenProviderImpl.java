package com.hebertzin.security_service.infra.tokens;

import com.hebertzin.security_service.infra.tokens.ports.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProviderImpl implements TokenProvider {
    private final SecretKey secretKey;
    private final String issuer;
    private final Long expirationInMinutes;

    public TokenProviderImpl(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.expiration-in-minutes}") Long expirationInMinutes
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.expirationInMinutes = expirationInMinutes;
    }


    public String generateToken(String subject) {
        Instant now = Instant.now();
        Instant exp = now.plus(expirationInMinutes, ChronoUnit.MINUTES);
        return  Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(secretKey)
                .compact();
    }


    public Jws<Claims> parseAndValidate(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token);
    }


    public String extracSubject(String token) {
        return parseAndValidate(token).getPayload().getSubject();
    }
}
