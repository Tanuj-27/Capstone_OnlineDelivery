package com.ey.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ey.dto.request.ForgotPasswordRequest;
import com.ey.dto.request.LoginRequest;
import com.ey.dto.request.RegisterRequest;
import com.ey.dto.request.ResetPasswordRequest;
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
   
   @PostMapping("/forgot-password")
   public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
      return authService.forgotPassword(request);
   }
   @PutMapping("/reset-password")
   public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
      return authService.resetPassword(request);
   }
   
   @PostMapping("/logout")
   public ResponseEntity<?> logout(){
	   return authService.logout();
   }
   
}