package com.ey.dto.response;

import java.util.List;

public class CartResponse {

    private Long cartId;

    private Long customerId;

    private boolean active;

    private int totalAmount;

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

    public boolean isActive() {

        return active;

    }

    public void setActive(boolean active) {

        this.active = active;

    }

    public int getTotalAmount() {

        return totalAmount;

    }

    public void setTotalAmount(int totalAmount) {

        this.totalAmount = totalAmount;

    }

    public List<CartItemResponse> getItems() {

        return items;

    }

    public void setItems(List<CartItemResponse> items) {

        this.items = items;

    }

}
 