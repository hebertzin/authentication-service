package com.hebertzin.security_service.repository.entities;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Device() {
    }

    public Device(
            String deviceType,
            String platform,
            String userAgent,
            String ipFirst,
            String ipLast,
            User user
    ) {
        this.deviceType = deviceType;
        this.platform = platform;
        this.userAgent = userAgent;
        this.ipFirst = ipFirst;
        this.ipLast = ipLast;
        this.user = user;
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

    public void setLastIp(String ipLast) {
        this.ipLast = ipLast;
    }
}