package com.ey.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.LoginRequest;
import com.ey.dto.request.RegisterRequest;
import com.ey.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
   private final AuthService authService;
   public AuthController(AuthService authService) {
       this.authService = authService;
   }
   
   @PostMapping("/register")
   public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
       return authService.register(request);
   }
  
   @PostMapping("/login")
   public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
       return authService.login(request);
   }
   
}