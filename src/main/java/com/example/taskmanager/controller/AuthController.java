package com.example.taskmanager.controller;

import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.entity.UserEntity;
import com.example.taskmanager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService svc;

    public AuthController(AuthService svc) { this.svc = svc; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {
        String token = svc.register(user);
        if (token == null) return ResponseEntity.badRequest().body("Username already taken");
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        String token = svc.login(req.getUsername(), req.getPassword());
        if (token == null) return ResponseEntity.status(401).body("Invalid credentials");
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
