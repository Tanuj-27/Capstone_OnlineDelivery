package com.ey.service;

import java.time.LocalDateTime;

import java.util.Map;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.ey.dto.request.LoginRequest;

import com.ey.dto.request.RegisterRequest;

import com.ey.enums.Role;

import com.ey.exception.ApiException;

import com.ey.model.User;

import com.ey.repository.UserRepository;

import com.ey.security.JwtUtil;

@Service

public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.jwtUtil = jwtUtil;

    }

    public ResponseEntity<?> register(RegisterRequest request) {

        logger.info("Registering user with email {}", request.getEmail());

        if (request.getRole() == Role.ADMIN) {

            throw new ApiException("Admin registration is not allowed");

        }

        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {

            throw new ApiException("Email already exists");

        });

        User user = new User();

        user.setName(request.getName());

        user.setPhone(request.getPhone());

        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        user.setCreatedAt(LocalDateTime.now());

        user.setUpdatedAt(LocalDateTime.now());

        user.setDeleted(false);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");

    }

    public ResponseEntity<?> login(LoginRequest request) {

        logger.info("Login attempt for email {}", request.getEmail());

        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())

                .orElseThrow(() -> new ApiException("Invalid credentials"));

        boolean match = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!match) {

            throw new ApiException("Invalid credentials");

        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(

                Map.of(

                        "token", token,

                        "role", user.getRole().name()

                )

        );

    }

}
 