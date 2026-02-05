package com.hebertzin.security_service.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id()
    String id;
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password")
    String password;


    public User (String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public  String getId() {
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
