package com.ey.dto.request;
import jakarta.validation.constraints.NotBlank;

public class AssignDeliveryPartnerRequest {
   @NotBlank(message = "Partner name is required")
   private String partner;
   public String getPartner() {
       return partner;
   }
   public void setPartner(String partner) {
       this.partner = partner;
   }
}