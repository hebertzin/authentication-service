package com.hebertzin.security_service.repository;

import com.hebertzin.security_service.repository.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
