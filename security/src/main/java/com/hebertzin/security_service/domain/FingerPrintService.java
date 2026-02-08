package com.hebertzin.security_service.domain;

public interface FingerPrintService {
    String hmacSha256(String secret, String data);
    String normalize(String... parts);
    String generate(String userAgent, String platform, String deviceType);
}
