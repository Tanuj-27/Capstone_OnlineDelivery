package com.ey.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemCreateRequest {
   @NotBlank(message = "Menu item name is required")
   private String name;
   @NotBlank(message = "Menu item description is required")
   private String description;
   @NotNull(message = "Price is required")
   @Min(value = 1, message = "Price must be greater than 0")
   private Integer price;
   
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
   public Integer getPrice() {
       return price;
   }
   public void setPrice(Integer price) {
       this.price = price;
   }
}