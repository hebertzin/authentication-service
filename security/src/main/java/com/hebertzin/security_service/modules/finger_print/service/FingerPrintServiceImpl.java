package com.hebertzin.security_service.modules.finger_print.service;

import com.hebertzin.security_service.modules.finger_print.ports.FingerPrintService;
import com.hebertzin.security_service.exceptions.InternalServerErrorException;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.stream.Collectors;

@Service
public class FingerPrintServiceImpl implements FingerPrintService {
    private static final String SECRET = "some-secret";

    public String generate(
            String userAgent,
            String platform,
            String deviceType
    ) {
        String raw = normalize(userAgent, platform, deviceType);
        return this.hmacSha256(SECRET, raw);
    }

    public String normalize(String... parts) {
        return Arrays.stream(parts)
                .map(p -> p == null ? "" : p.trim().toLowerCase())
                .collect(Collectors.joining("|"));
    }

    public String hmacSha256(String secret, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key =
                    new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

            mac.init(key);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(rawHmac);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error generating HMAC");
        }
    }
}
