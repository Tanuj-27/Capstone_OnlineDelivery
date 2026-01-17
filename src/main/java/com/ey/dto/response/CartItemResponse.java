package com.ey.dto.response;
public class CartItemResponse {
   private Long cartItemId;
   private Long menuItemId;
   private String itemName;
   private int quantity;
   private int unitPrice;
   private int totalPrice;
   public Long getCartItemId() {
       return cartItemId;
   }
   public void setCartItemId(Long cartItemId) {
       this.cartItemId = cartItemId;
   }
   public Long getMenuItemId() {
       return menuItemId;
   }
   public void setMenuItemId(Long menuItemId) {
       this.menuItemId = menuItemId;
   }
   public String getItemName() {
       return itemName;
   }
   public void setItemName(String itemName) {
       this.itemName = itemName;
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
   public int getTotalPrice() {
       return totalPrice;
   }
   public void setTotalPrice(int totalPrice) {
       this.totalPrice = totalPrice;
   }
}