package com.ey.model;
import java.time.LocalDateTime;
import com.ey.enums.OrderStatus;
import com.ey.enums.PaymentMethod;
import com.ey.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderId;
   private Long customerId;
   private Long restaurantId;
   private Long addressId;
   private PaymentMethod paymentMethod;
   private PaymentStatus paymentStatus;
   private OrderStatus status;
   private String deliveryPartner;
   private Integer totalAmount;
   private LocalDateTime scheduledDeliveryTime;
   private LocalDateTime createdAt;
   private LocalDateTime updatedAt;
   private boolean isDeleted;
   
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
   public PaymentMethod getPaymentMethod() {
       return paymentMethod;
   }
   public void setPaymentMethod(PaymentMethod paymentMethod) {
       this.paymentMethod = paymentMethod;
   }
   public PaymentStatus getPaymentStatus() {
       return paymentStatus;
   }
   public void setPaymentStatus(PaymentStatus paymentStatus) {
       this.paymentStatus = paymentStatus;
   }
   public OrderStatus getStatus() {
       return status;
   }
   public void setStatus(OrderStatus status) {
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
   public LocalDateTime getUpdatedAt() {
       return updatedAt;
   }
   public void setUpdatedAt(LocalDateTime updatedAt) {
       this.updatedAt = updatedAt;
   }
   public boolean isDeleted() {
       return isDeleted;
   }
   public void setDeleted(boolean isDeleted) {
       this.isDeleted = isDeleted;
   }
}