package com.ey.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.ey.dto.request.CartItemCreateRequest;

import com.ey.dto.request.CartItemUpdateRequest;

import com.ey.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/cart/{customerId}")
	public ResponseEntity<?> getCart(@PathVariable Long customerId) {
		return cartService.getCart(customerId);
	}

	@PostMapping("/cart/items")
	public ResponseEntity<?> addItemToCart(@Valid @RequestBody CartItemCreateRequest request) {
		return cartService.addItemToCart(request);
	}

	@PutMapping("/cart/items")
	public ResponseEntity<?> updateCartItem(@Valid @RequestBody CartItemUpdateRequest request) {
		return cartService.updateCartItem(request);
	}

	@DeleteMapping("/cart/{customerId}/items/{cartItemId}")
	public ResponseEntity<?> deleteCartItem(@PathVariable Long customerId, @PathVariable Long cartItemId) {
		return cartService.deleteCartItem(customerId, cartItemId);
	}

	@DeleteMapping("/cart/{customerId}/clear")
	public ResponseEntity<?> clearCart(@PathVariable Long customerId) {
		return cartService.clearCart(customerId);
	}

}
