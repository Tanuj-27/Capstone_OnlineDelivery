package com.ey.dto.response;
import java.util.List;

public class CartResponse {
   private Long cartId;
   private Long customerId;
   private Boolean active;
   private Integer totalAmount;
   private List<CartItemResponse> items;
   
   public Long getCartId() {
       return cartId;
   }
   public void setCartId(Long cartId) {
       this.cartId = cartId;
   }
   public Long getCustomerId() {
       return customerId;
   }
   public void setCustomerId(Long customerId) {
       this.customerId = customerId;
   }
   public Boolean getActive() {
       return active;
   }
   public void setActive(Boolean active) {
       this.active = active;
   }
   public Integer getTotalAmount() {
       return totalAmount;
   }
   public void setTotalAmount(Integer totalAmount) {
       this.totalAmount = totalAmount;
   }
   public List<CartItemResponse> getItems() {
       return items;
   }
   public void setItems(List<CartItemResponse> items) {
       this.items = items;
   }
}