package com.ey.dto.response;

public class CartItemResponse {
   private Long cartItemId;
   private String itemName;
   private Integer quantity;
   private Integer unitPrice;
   private Integer totalPrice;
   
   public Long getCartItemId() {
       return cartItemId;
   }
   public void setCartItemId(Long cartItemId) {
       this.cartItemId = cartItemId;
   }
   public String getItemName() {
       return itemName;
   }
   public void setItemName(String itemName) {
       this.itemName = itemName;
   }
   public Integer getQuantity() {
       return quantity;
   }
   public void setQuantity(Integer quantity) {
       this.quantity = quantity;
   }
   public Integer getUnitPrice() {
       return unitPrice;
   }
   public void setUnitPrice(Integer unitPrice) {
       this.unitPrice = unitPrice;
   }
   public Integer getTotalPrice() {
       return totalPrice;
   }
   public void setTotalPrice(Integer totalPrice) {
       this.totalPrice = totalPrice;
   }
}