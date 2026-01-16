package com.ey.service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.ey.dto.request.LoginRequest;
import com.ey.dto.request.RegisterRequest;
import com.ey.exception.ApiException;
import com.ey.mapper.UserMapper;
import com.ey.model.User;
import com.ey.repository.UserRepository;
import java.time.LocalDateTime;

@Service
public class AuthService {
   private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
   private final UserRepository userRepository;
   public AuthService(UserRepository userRepository) {
       this.userRepository = userRepository;
   }
   public ResponseEntity<?> register(RegisterRequest request) {
       logger.info("Register request received for email: {}", request.getEmail());
       Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
       if (existingUser.isPresent()) {
           logger.warn("Register failed, email already exists: {}", request.getEmail());
           throw new ApiException("Email already exists");
       }
       User user = UserMapper.toEntity(request);
       user.setCreatedAt(LocalDateTime.now());
       user.setUpdatedAt(LocalDateTime.now());
       userRepository.save(user);
       
       Map<String, Object> response = new HashMap<>();
       response.put("message", "User registered successfully");
       response.put("createdBy", "SYSTEM");
       response.put("createdAt", LocalDateTime.now());
       logger.info("User registered successfully: {}", request.getEmail());
       return new ResponseEntity<>(response, HttpStatus.CREATED);
   }
   
   public ResponseEntity<?> login(LoginRequest request) {
       logger.info("Login request received for email: {}", request.getEmail());
       Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
       if (userOpt.isEmpty()) {
           logger.warn("Login failed - user not found for email: {}", request.getEmail());
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
       }
       
       User user = userOpt.get();
       if (!user.getPassword().equals(request.getPassword())) {
           logger.warn("Login failed - wrong password for email: {}", request.getEmail());
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
       }
       
       Map<String, Object> response = new HashMap<>();
       response.put("token", "jwt-token");
       response.put("role", user.getRole());
       logger.info("Login successful for email: {}", request.getEmail());
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
}