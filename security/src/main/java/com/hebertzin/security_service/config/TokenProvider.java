package com.hebertzin.security_service.config;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private  final SecretKey secret;
    private final String issuer;
    private final Long expirationMinutes;

    public TokenProvider(
            @Value("${app.jwt.secret}") SecretKey secret,
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.expirationInMinutes}") Long expirationMinutes) {
         this.secret = secret;
         this.issuer =  issuer;
         this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);
        return  Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(secret)
                .compact();
    }

}
