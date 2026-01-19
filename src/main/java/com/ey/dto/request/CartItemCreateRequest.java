package com.ey.dto.request;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;

public class CartItemCreateRequest {

	@NotNull(message = "CustomerId is required")
	private Long customerId;

	@NotNull(message = "MenuItemId is required")
	private Long menuItemId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;

	public Long getCustomerId() {

		return customerId;

	}

	public void setCustomerId(Long customerId) {

		this.customerId = customerId;

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

}
