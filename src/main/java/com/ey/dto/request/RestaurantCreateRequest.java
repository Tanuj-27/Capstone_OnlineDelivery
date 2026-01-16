package com.ey.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestaurantCreateRequest {
   @NotNull(message = "OwnerId is required")
   private Long ownerId;
   
   @NotBlank(message = "Restaurant name is required")
   private String name;
   
   @NotBlank(message = "Address is required")
   private String address;
   
   @NotBlank(message = "City is required")
   private String city;
   public Long getOwnerId() {
       return ownerId;
   }
   public void setOwnerId(Long ownerId) {
       this.ownerId = ownerId;
   }
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public String getAddress() {
       return address;
   }
   public void setAddress(String address) {
       this.address = address;
   }
   public String getCity() {
       return city;
   }
   public void setCity(String city) {
       this.city = city;
   }
}