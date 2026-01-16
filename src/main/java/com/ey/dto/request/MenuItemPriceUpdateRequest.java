package com.ey.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MenuItemPriceUpdateRequest {
   @NotNull(message = "Price is required")
   @Min(value = 1, message = "Price must be greater than 0")
   private Integer price;
   public Integer getPrice() {
       return price;
   }
   public void setPrice(Integer price) {
       this.price = price;
   }
}