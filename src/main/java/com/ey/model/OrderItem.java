package com.ey.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class OrderItem {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderItemId;
   private Long orderId;
   private Long menuItemId;
   private int quantity;
   private int unitPrice;
   private boolean isDeleted;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   public Long getOrderItemId() {
       return orderItemId;
   }
   public void setOrderItemId(Long orderItemId) {
       this.orderItemId = orderItemId;
   }
   public Long getOrderId() {
       return orderId;
   }
   public void setOrderId(Long orderId) {
       this.orderId = orderId;
   }
   public Long getMenuItemId() {
       return menuItemId;
   }
   public void setMenuItemId(Long menuItemId) {
       this.menuItemId = menuItemId;
   }
   public int getQuantity() {
       return quantity;
   }
   public void setQuantity(int quantity) {
       this.quantity = quantity;
   }
   public int getUnitPrice() {
       return unitPrice;
   }
   public void setUnitPrice(int unitPrice) {
       this.unitPrice = unitPrice;
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
}