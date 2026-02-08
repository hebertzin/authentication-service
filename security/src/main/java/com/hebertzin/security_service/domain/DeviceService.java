package com.hebertzin.security_service.domain;

import com.hebertzin.security_service.repository.entities.Device;

import java.util.UUID;

public interface DeviceService {
    Device createOrFindDevice(UUID userId, String deviceType, String platform, String userAgent, String ip);
    Device resolveDevice(UUID userId, String deviceType, String platform, String userAgent, String ip);
    Device createNewDevice(UUID userId, String fingerprint, String deviceType, String platform, String userAgent, String ip);
    void validateDeviceLimit(UUID userId);
}
