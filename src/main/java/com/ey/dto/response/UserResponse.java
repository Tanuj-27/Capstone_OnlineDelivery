package com.ey.dto.response;

import com.ey.enums.Role;

public class UserResponse {
   private Long userId;
   private String name;
   private String phone;
   private String email;
   private Role role;
   public Long getUserId() {
       return userId;
   }
   public void setUserId(Long userId) {
       this.userId = userId;
   }
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
   public Role getRole() {
       return role;
   }
   public void setRole(Role role) {
       this.role = role;
   }
}