package com.ey.dto.request;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;

public class CartItemUpdateRequest {

	@NotNull(message = "CustomerId is required")
	private Long customerId;

	@NotNull(message = "CartItemId is required")
	private Long cartItemId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;

	public Long getCustomerId() {

		return customerId;

	}

	public void setCustomerId(Long customerId) {

		this.customerId = customerId;

	}

	public Long getCartItemId() {

		return cartItemId;

	}

	public void setCartItemId(Long cartItemId) {

		this.cartItemId = cartItemId;

	}

	public int getQuantity() {

		return quantity;

	}

	public void setQuantity(int quantity) {

		this.quantity = quantity;

	}

}
