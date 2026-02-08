package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.DeviceService;
import com.hebertzin.security_service.domain.FingerPrintService;
import com.hebertzin.security_service.exceptions.BadRequestException;
import com.hebertzin.security_service.presentation.TrustLevel;
import com.hebertzin.security_service.repository.DeviceRepository;
import com.hebertzin.security_service.repository.entities.Device;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {
   private final DeviceRepository repo;
   private  final  FingerPrintService fingerPrintService;
   Integer MAX_DEVICES_ALLOW = 5;

    public DeviceServiceImpl( DeviceRepository repo, FingerPrintServiceImpl fingerPrintService) {
        this.repo = repo;
        this.fingerPrintService = fingerPrintService;
    }

    public Device createOrFindDevice(
            UUID userId,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {
        return resolveDevice(
                userId,
                deviceType,
                platform,
                userAgent,
                ip
        );
    }

    public Device resolveDevice(
            UUID userId,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {

        String fingerPrintHash = this.fingerPrintService.generate(userAgent, platform, deviceType);

        return repo.findByUserIdAndFingerprint(userId, fingerPrintHash)
                .map(device -> {
                    device.setLastIp(ip);
                    device.setUserAgent(userAgent);
                    device.setPlatform(platform);
                    return repo.save(device);
                })
                .orElseGet(() -> createNewDevice(
                        userId, fingerPrintHash, deviceType, platform, userAgent, ip
                ));
    }

    public Device createNewDevice(
            UUID userId,
            String fingerPrintHash,
            String deviceType,
            String platform,
            String userAgent,
            String ip
    ) {

        validateDeviceLimit(userId);

        Device device = new Device();

        device.setFingerPrint(fingerPrintHash);
        device.setDeviceType(deviceType);
        device.setPlatform(platform);
        device.setUserAgent(userAgent);
        device.setFirstIp(ip);
        device.setLastIp(ip);
        device.setTrustLevel(TrustLevel.UNTRUSTED);

        return repo.save(device);
    }

    public void validateDeviceLimit(UUID userId) {
        long totalDevices = repo.countByUserId(userId);

        if (totalDevices >= MAX_DEVICES_ALLOW) {
            throw new BadRequestException(
                    "Maximum number of devices reached"
            );
        }
    }

}
