package com.ey.dto.response;

public class CartItemResponse {

    private Long cartItemId;

    private Long menuItemId;

    private int quantity;

    private int unitPrice;

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

}
 