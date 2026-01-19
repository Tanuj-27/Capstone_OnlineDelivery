package com.ey.service;
import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
   private final AuthenticationManager authenticationManager;
   public AuthService(
           UserRepository userRepository,
           PasswordEncoder passwordEncoder,
           JwtUtil jwtUtil,
           AuthenticationManager authenticationManager
   ) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
       this.jwtUtil = jwtUtil;
       this.authenticationManager = authenticationManager;
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
       Authentication auth = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
       );
       String email = auth.getName();
       User user = userRepository.findByEmailAndIsDeletedFalse(email)
               .orElseThrow(() -> new ApiException("Invalid credentials"));
       String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
       return ResponseEntity.ok(
               Map.of(
                       "token", token,
                       "role", user.getRole().name()
               )
       );
   }
   public ResponseEntity<?> forgotPassword(com.ey.dto.request.ForgotPasswordRequest request) {
       User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
               .orElseThrow(() -> new ApiException("User not found"));
       String token = java.util.UUID.randomUUID().toString();
       user.setResetToken(token);
       user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
       user.setUpdatedAt(LocalDateTime.now());
       userRepository.save(user);
       return ResponseEntity.ok(
               Map.of(
                       "message", "Reset token generated",
                       "token", token
               )
       );
   }
   public ResponseEntity<?> resetPassword(com.ey.dto.request.ResetPasswordRequest request) {
       User user = userRepository.findByResetTokenAndIsDeletedFalse(request.getToken())
               .orElseThrow(() -> new ApiException("Invalid token"));
       if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
           throw new ApiException("Token expired");
       }
       user.setPassword(passwordEncoder.encode(request.getNewPassword()));
       user.setResetToken(null);
       user.setResetTokenExpiry(null);
       user.setUpdatedAt(LocalDateTime.now());
       userRepository.save(user);
       return ResponseEntity.ok("Password reset successful");
   }
}