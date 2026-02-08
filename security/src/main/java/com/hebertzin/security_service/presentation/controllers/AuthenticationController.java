package com.hebertzin.security_service.presentation.controllers;

import com.hebertzin.security_service.modules.authentication.dto.AuthenticationRequest;
import com.hebertzin.security_service.modules.authentication.ports.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class AuthenticationController {
    private final  AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> auhtenticate(@RequestBody AuthenticationRequest authRequest) {
        String auth = this.authenticationService.authenticate(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(auth);
    }
}
