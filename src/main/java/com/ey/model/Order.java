package com.ey.model;

import java.time.LocalDateTime;

import com.ey.enums.OrderStatus;
import com.ey.enums.PaymentMethod;
import com.ey.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long orderId;
   
   private Long customerId;
   private Long restaurantId;
   private Long addressId;
   @Enumerated(EnumType.STRING)
   private PaymentMethod paymentMethod;
   
   @Enumerated(EnumType.STRING)
   private PaymentStatus paymentStatus;
   
   @Enumerated(EnumType.STRING)
   private OrderStatus status;
   
   private String deliveryPartner;
   private int totalAmount;
   private LocalDateTime scheduledDeliveryTime;
   
   public Order() {
       super();
   }
   
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
   public int getTotalAmount() {
       return totalAmount;
   }
   public void setTotalAmount(int totalAmount) {
       this.totalAmount = totalAmount;
   }
   public LocalDateTime getScheduledDeliveryTime() {
       return scheduledDeliveryTime;
   }
   public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
       this.scheduledDeliveryTime = scheduledDeliveryTime;
   }
   @Override
   public String toString() {
       return "Order [orderId=" + orderId + ", customerId=" + customerId + ", restaurantId=" + restaurantId
               + ", addressId=" + addressId + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus
               + ", status=" + status + ", deliveryPartner=" + deliveryPartner + ", totalAmount=" + totalAmount
               + ", scheduledDeliveryTime=" + scheduledDeliveryTime + "]";
   }
}