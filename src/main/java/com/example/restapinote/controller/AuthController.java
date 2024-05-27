package com.example.restapinote.controller;

import com.example.restapinote.auth.AuthService;
import com.example.restapinote.auth.dto.login.LoginRequest;
import com.example.restapinote.auth.dto.login.LoginResponse;
import com.example.restapinote.auth.dto.RegistrationRequest;
import com.example.restapinote.auth.dto.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public LoginResponse register(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
