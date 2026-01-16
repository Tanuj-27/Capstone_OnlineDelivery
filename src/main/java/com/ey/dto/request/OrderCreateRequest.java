package com.ey.dto.request;
import java.time.LocalDateTime;
import com.ey.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public class OrderCreateRequest {
	
   @NotNull(message = "Address id cannot be null")
   private Long addressId;
   
   @NotNull(message = "Payment method is required")
   private PaymentMethod paymentMethod;
   
   private LocalDateTime scheduledDeliveryTime;
   
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
   public LocalDateTime getScheduledDeliveryTime() {
       return scheduledDeliveryTime;
   }
   public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {
       this.scheduledDeliveryTime = scheduledDeliveryTime;
   }
}