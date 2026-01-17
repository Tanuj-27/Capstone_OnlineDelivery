package com.ey.dto.request;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class OrderCreateRequest {
   @NotNull(message = "CustomerId is required")
   private Long customerId;
  
   @NotNull(message = "AddressId is required")
   private Long addressId;
  
   @NotNull(message = "Payment method is required")
   private String paymentMethod;
   private LocalDateTime scheduledDeliveryTime;
  
   public Long getCustomerId() {
       return customerId;
   }
   public void setCustomerId(Long customerId) {
       this.customerId = customerId;
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
   public LocalDateTime getScheduledDeliveryTime() {
       return scheduledDeliveryTime;
   }
   public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
       this.scheduledDeliveryTime = scheduledDeliveryTime;
   }
}