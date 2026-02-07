package com.hebertzin.security_service.presentation.controllers;

import com.hebertzin.security_service.domain.UserService;
import com.hebertzin.security_service.presentation.CreateUserRequest;
import com.hebertzin.security_service.repository.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
         this.userService = userService;
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> post(@RequestBody CreateUserRequest body) {
            User saved = this.userService.save(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
    }
}
