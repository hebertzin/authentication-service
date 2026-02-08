package com.hebertzin.security_service.modules.devices.repository.entity;
import com.hebertzin.security_service.modules.users.repository.entity.User;
import com.hebertzin.security_service.modules.devices.ports.TrustLevelDevice;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "platform", nullable = false)
    private String platform;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "ip_first", nullable = false)
    private String ipFirst;

    @Column(name = "ip_last", nullable = false)
    private String ipLast;

    @Column(name = "trust_level", nullable = false)
    private TrustLevelDevice trust_level;

    @Column(name = "fingerprint", nullable = false)
    private String fingerPrint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Device() {
    }

    public UUID getId() { return id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getDeviceType() {
        return deviceType;
    }

    public String getPlatform() {
        return platform;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpFirst() {
        return ipFirst;
    }

    public String getIpLast() {
        return ipLast;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public TrustLevelDevice getTrustLevel() {
        return  this.trust_level;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setLastIp(String ipLast) {
        this.ipLast = ipLast;
    }

    public void setFirstIp(String ipFirst) {
        this.ipFirst = ipFirst;
    }

    public void setTrustLevel(TrustLevelDevice trustLevelDevice) {
        this.trust_level = trustLevelDevice;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Device(
            String deviceType,
            String platform,
            String userAgent,
            String ipFirst,
            String ipLast,
            TrustLevelDevice trustLevelDevice,
            User user
    ) {
        this.deviceType = deviceType;
        this.platform = platform;
        this.userAgent = userAgent;
        this.ipFirst = ipFirst;
        this.ipLast = ipLast;
        this.trust_level = trustLevelDevice;
        this.user = user;

    }
}