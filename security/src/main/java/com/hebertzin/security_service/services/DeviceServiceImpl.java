package com.hebertzin.security_service.services;
import com.hebertzin.security_service.domain.UserService;
import com.hebertzin.security_service.repository.DeviceRepository;
import com.hebertzin.security_service.repository.entities.Device;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl {
    DeviceRepository repo;
    UserService userService;

    public DeviceServiceImpl( DeviceRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    List<Device>createOrFindDevice(UUID userId,String deviceType ,String ipFirst,String trustLevel, String ipLast, String platform, User user, String userAgent) throws Exception {
        List<Device> devices = this.repo.findByUserId(userId);

        if (devices.size() > 2 ) {
            throw  new Exception("You cannot have more than 2 devices");
        }

        Device d = new Device(deviceType, platform, userAgent, ipFirst, ipLast, trustLevel, user);

        return  devices;
    }

}
