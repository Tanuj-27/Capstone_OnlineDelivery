package com.ey.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ey.dto.request.LoginRequest;

import com.ey.model.User;

import com.ey.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserRepository userRepository

    ) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/api/auth/login");

    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response

    ) {

        try {
            LoginRequest loginRequest =
                    new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    );

            return authenticationManager.authenticate(authenticationToken);

        } catch (Exception e) {
            throw new RuntimeException("Invalid login request");
        }

    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult

    ) throws IOException {

        String email = authResult.getName();
        User user = userRepository.findByEmailAndIsDeletedFalse(email).get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        response.setContentType("application/json");
        response.getWriter()
                .write("{\"token\":\"" + token + "\",\"role\":\"" + user.getRole() + "\"}");
    }

}
 