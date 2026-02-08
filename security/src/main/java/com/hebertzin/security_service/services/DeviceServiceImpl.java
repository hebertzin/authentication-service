package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.UserService;
import com.hebertzin.security_service.presentation.TrustLevel;
import com.hebertzin.security_service.repository.DeviceRepository;
import com.hebertzin.security_service.repository.entities.Device;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DeviceServiceImpl {
    DeviceRepository repo;
    UserService userService;

    Integer MAX_DEVICES_ALLOW = 5;

    public DeviceServiceImpl( DeviceRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    public Device createOrFindDevice(
            UUID userId,
            String fingerprint,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {
        return resolveDevice(
                userId,
                fingerprint,
                deviceType,
                platform,
                userAgent,
                ip
        );
    }

    private Device resolveDevice(
            UUID userId,
            String fingerprint,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {

        return repo.findByUserIdAndFingerprint(userId, fingerprint)
                .map(device -> {
                    device.setLastIp(ip);
                    device.setUserAgent(userAgent);
                    return repo.save(device);
                })
                .orElseGet(() -> createNewDevice(
                        userId, fingerprint, deviceType, platform, userAgent, ip
                ));
    }

    private Device createNewDevice(
            UUID userId,
            String fingerprint,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {

        validateDeviceLimit(userId);

        Device device = new Device();

        device.setFingerPrint(fingerprint);
        device.setDeviceType(deviceType);
        device.setPlatform(platform);
        device.setUserAgent(userAgent);
        device.setFirstIp(ip);
        device.setLastIp(ip);
        device.setTrustLevel(TrustLevel.UNTRUSTED);

        return repo.save(device);
    }

    private void validateDeviceLimit(UUID userId) {
        long totalDevices = repo.countByUserId(userId);

        if (totalDevices >= MAX_DEVICES_ALLOW) {
            throw new IllegalStateException(
                    "Maximum number of devices reached"
            );
        }
    }



}
