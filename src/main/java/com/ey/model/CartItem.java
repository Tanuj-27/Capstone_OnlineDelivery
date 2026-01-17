package com.ey.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity

@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long cartItemId;
    private Long cartId;
    private Long menuItemId;
    private int quantity;
    private int unitPrice;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getCartItemId() {

        return cartItemId;

    }

    public void setCartItemId(Long cartItemId) {

        this.cartItemId = cartItemId;

    }

    public Long getCartId() {

        return cartId;

    }

    public void setCartId(Long cartId) {

        this.cartId = cartId;

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

    public boolean isDeleted() {

        return isDeleted;

    }

    public void setDeleted(boolean isDeleted) {

        this.isDeleted = isDeleted;

    }

    public LocalDateTime getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;

    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;

    }

    public void setUpdatedAt(LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;

    }

}
 