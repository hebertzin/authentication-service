package com.hebertzin.security_service.repository.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password")
    String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Device> devices;

    protected User () {

    }
    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public  UUID getId() {
        return  this.id;
    }

    public  String getName() {
        return  this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

}
