package com.ey.dto.request;

import com.ey.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
   @Size(min = 2, message = "Name must have atleast 2 characters")
   private String name;
   
   @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone must have 10 digits")
   private String phone;
   
   @NotEmpty(message = "Email cannot be empty")
   @Email(message = "Invalid email")
   private String email;
   
   @NotEmpty(message = "Password cannot be empty")
   private String password;
   
   private Role role;
   
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public String getPhone() {
       return phone;
   }
   public void setPhone(String phone) {
       this.phone = phone;
   }
   public String getEmail() {
       return email;
   }
   public void setEmail(String email) {
       this.email = email;
   }
   public String getPassword() {
       return password;
   }
   public void setPassword(String password) {
       this.password = password;
   }
   public Role getRole() {
       return role;
   }
   public void setRole(Role role) {
       this.role = role;
   }
}