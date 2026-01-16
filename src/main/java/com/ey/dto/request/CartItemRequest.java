package com.ey.dto.request;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;

public class CartItemRequest {

    @NotNull(message = "Menu item id cannot be null")
    private Long menuItemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
 