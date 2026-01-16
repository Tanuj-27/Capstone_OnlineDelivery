package com.ey.dto.response;

public class RestaurantResponse {
   private Long restaurantId;
   private Long ownerId;
   private String name;
   private String address;
   private String city;
  
   public Long getRestaurantId() {
       return restaurantId;
   }
   public void setRestaurantId(Long restaurantId) {
       this.restaurantId = restaurantId;
   }
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