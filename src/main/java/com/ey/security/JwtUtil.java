package com.ey.security;

import java.nio.charset.StandardCharsets;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.ey.enums.Role;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {

    @Value("${app.jwt.secret}")

    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")

    private long jwtExpirationMs;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    }

    public String generateToken(String email, Role role) {

        return Jwts.builder()

                .setSubject(email)

                .claim("role", role.name())

                .setIssuedAt(new Date())

                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))

                .signWith(getSigningKey(), SignatureAlgorithm.HS256)

                .compact();

    }

    public Claims extractClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

    public String extractEmail(String token) {

        return extractClaims(token).getSubject();

    }

    public Role extractRole(String token) {

        return Role.valueOf(extractClaims(token).get("role", String.class));

    }

}
 