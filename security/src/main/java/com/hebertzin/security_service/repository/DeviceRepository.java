package com.hebertzin.security_service.repository;

import com.hebertzin.security_service.repository.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Optional<Device> findByUserIdAndFingerprint(UUID userId, String fingerprint);
    long countByUserId(UUID userId);
}
