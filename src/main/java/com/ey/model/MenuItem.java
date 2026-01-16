package com.ey.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class MenuItem {
   
	@Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long menuItemId;
   private Long restaurantId;
   private String name;
   private String description;
   private int price;
   private boolean isDeleted;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
  
   public MenuItem() {
       super();
   }
   
   public Long getMenuItemId() {
       return menuItemId;
   }
   public void setMenuItemId(Long menuItemId) {
       this.menuItemId = menuItemId;
   }
   public Long getRestaurantId() {
       return restaurantId;
   }
   public void setRestaurantId(Long restaurantId) {
       this.restaurantId = restaurantId;
   }
   public String getName() {
       return name;
   }
   public void setName(String name) {
       this.name = name;
   }
   public String getDescription() {
       return description;
   }
   public void setDescription(String description) {
       this.description = description;
   }
   public int getPrice() {
       return price;
   }
   public void setPrice(int price) {
       this.price = price;
   }
   public boolean isDeleted() {
       return isDeleted;
   }
   public void setDeleted(boolean isDeleted) {
       this.isDeleted = isDeleted;
   }
   public LocalDateTime getCreatedAt() {
       return createdAt;
   }
   public void setCreatedAt(LocalDateTime createdAt) {
       this.createdAt = createdAt;
   }
   public LocalDateTime getUpdatedAt() {
       return updatedAt;
   }
   public void setUpdatedAt(LocalDateTime updatedAt) {
       this.updatedAt = updatedAt;
   }
  
   @Override
   public String toString() {
       return "MenuItem [menuItemId=" + menuItemId + ", restaurantId=" + restaurantId + ", name=" + name
               + ", description=" + description + ", price=" + price + ", isDeleted=" + isDeleted + ", createdAt="
               + createdAt + ", updatedAt=" + updatedAt + "]";
   }
}