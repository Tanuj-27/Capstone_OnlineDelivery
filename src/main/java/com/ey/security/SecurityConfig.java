package com.ey.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final JwtUtil jwtUtil;

    private final com.ey.repository.UserRepository userRepository;

    public SecurityConfig(

            JwtAuthorizationFilter jwtAuthorizationFilter,

            JwtUtil jwtUtil,

            com.ey.repository.UserRepository userRepository

    ) {

        this.jwtAuthorizationFilter = jwtAuthorizationFilter;

        this.jwtUtil = jwtUtil;

        this.userRepository = userRepository;

    }

    @Bean

    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean

    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }

    @Bean

    public SecurityFilterChain filterChain(

            HttpSecurity http,

            AuthenticationManager authenticationManager

    ) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter =

                new JwtAuthenticationFilter(authenticationManager, jwtUtil, userRepository);

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth

                .requestMatchers(

                        "/api/auth/register",

                        "/api/auth/login",

                        "/api/auth/forgot-password",

                        "/api/auth/reset-password"

                ).permitAll()

                .requestMatchers("/api/owner/**").hasAnyRole("OWNER", "ADMIN")

                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()

        );

        http.addFilter(jwtAuthenticationFilter);

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
 