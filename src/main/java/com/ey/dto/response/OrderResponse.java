package com.ey.dto.response;
import java.time.LocalDateTime;

public class OrderResponse {
  
	private Long orderId;
   private Long customerId;
   private Long restaurantId;
   private Long addressId;
   private String paymentMethod;
   private String paymentStatus;
   private String status;
   private String deliveryPartner;
   private Integer totalAmount;
   private LocalDateTime scheduledDeliveryTime;
   private LocalDateTime createdAt;
   
   public Long getOrderId() {
       return orderId;
   }
   public void setOrderId(Long orderId) {
       this.orderId = orderId;
   }
   public Long getCustomerId() {
       return customerId;
   }
   public void setCustomerId(Long customerId) {
       this.customerId = customerId;
   }
   public Long getRestaurantId() {
       return restaurantId;
   }
   public void setRestaurantId(Long restaurantId) {
       this.restaurantId = restaurantId;
   }
   public Long getAddressId() {
       return addressId;
   }
   public void setAddressId(Long addressId) {
       this.addressId = addressId;
   }
   public String getPaymentMethod() {
       return paymentMethod;
   }
   public void setPaymentMethod(String paymentMethod) {
       this.paymentMethod = paymentMethod;
   }
   public String getPaymentStatus() {
       return paymentStatus;
   }
   public void setPaymentStatus(String paymentStatus) {
       this.paymentStatus = paymentStatus;
   }
   public String getStatus() {
       return status;
   }
   public void setStatus(String status) {
       this.status = status;
   }
   public String getDeliveryPartner() {
       return deliveryPartner;
   }
   public void setDeliveryPartner(String deliveryPartner) {
       this.deliveryPartner = deliveryPartner;
   }
   public Integer getTotalAmount() {
       return totalAmount;
   }
   public void setTotalAmount(Integer totalAmount) {
       this.totalAmount = totalAmount;
   }
   public LocalDateTime getScheduledDeliveryTime() {
       return scheduledDeliveryTime;
   }
   public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
       this.scheduledDeliveryTime = scheduledDeliveryTime;
   }
   public LocalDateTime getCreatedAt() {
       return createdAt;
   }
   public void setCreatedAt(LocalDateTime createdAt) {
       this.createdAt = createdAt;
   }
}