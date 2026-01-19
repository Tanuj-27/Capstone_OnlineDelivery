package com.ey.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ey.dto.request.CartItemCreateRequest;
import com.ey.dto.request.CartItemUpdateRequest;
import com.ey.dto.response.CartItemResponse;
import com.ey.dto.response.CartResponse;
import com.ey.exception.ApiException;
import com.ey.model.Cart;
import com.ey.model.CartItem;
import com.ey.model.MenuItem;
import com.ey.repository.CartItemRepository;
import com.ey.repository.CartRepository;
import com.ey.repository.MenuItemRepository;

@Service
public class CartService {
	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;
	private final MenuItemRepository menuItemRepository;

	public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
			MenuItemRepository menuItemRepository) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.menuItemRepository = menuItemRepository;
	}

	public ResponseEntity<?> addItemToCart(CartItemCreateRequest request) {
		Cart cart = cartRepository.findByCustomerIdAndActiveTrueAndIsDeletedFalse(request.getCustomerId())
				.orElseGet(() -> {
					Cart c = new Cart();
					c.setCustomerId(request.getCustomerId());
					c.setActive(true);
					c.setTotalAmount(0);
					c.setDeleted(false);
					c.setCreatedAt(LocalDateTime.now());
					c.setUpdatedAt(LocalDateTime.now());
					return cartRepository.save(c);
				});
		MenuItem menuItem = menuItemRepository.findByMenuItemIdAndIsDeletedFalse(request.getMenuItemId())
				.orElseThrow(() -> new ApiException("Menu item not found"));
		CartItem cartItem = new CartItem();
		cartItem.setCartId(cart.getCartId());
		cartItem.setMenuItemId(menuItem.getMenuItemId());
		cartItem.setQuantity(request.getQuantity());
		cartItem.setUnitPrice(menuItem.getPrice());
		cartItem.setDeleted(false);
		cartItem.setCreatedAt(LocalDateTime.now());
		cartItem.setUpdatedAt(LocalDateTime.now());
		cartItemRepository.save(cartItem);
		int total = cartItemRepository.findByCartIdAndIsDeletedFalse(cart.getCartId()).stream()
				.mapToInt(i -> i.getQuantity() * i.getUnitPrice()).sum();
		cart.setTotalAmount(total);
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);
		return ResponseEntity.ok("Item added to cart");
	}

	public ResponseEntity<?> getCart(Long customerId) {
		Cart cart = cartRepository.findByCustomerIdAndActiveTrueAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ApiException("No active cart found"));
		List<CartItem> items = cartItemRepository.findByCartIdAndIsDeletedFalse(cart.getCartId());
		CartResponse response = new CartResponse();
		response.setCartId(cart.getCartId());
		response.setCustomerId(cart.getCustomerId());
		response.setActive(cart.isActive());
		response.setTotalAmount(cart.getTotalAmount());
		List<CartItemResponse> itemResponses = items.stream().map(i -> {
			CartItemResponse r = new CartItemResponse();
			r.setCartItemId(i.getCartItemId());
			r.setMenuItemId(i.getMenuItemId());
			r.setQuantity(i.getQuantity());
			r.setUnitPrice(i.getUnitPrice());
			r.setTotalPrice(i.getQuantity() * i.getUnitPrice());
			String name = menuItemRepository.findByMenuItemIdAndIsDeletedFalse(i.getMenuItemId()).map(MenuItem::getName)
					.orElse("Unknown Item");
			r.setItemName(name);
			return r;
		}).toList();
		response.setItems(itemResponses);
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<?> updateCartItem(CartItemUpdateRequest request) {

		Cart cart = cartRepository
				.findByCustomerIdAndActiveTrueAndIsDeletedFalse(request.getCustomerId())
				.orElseThrow(() -> new ApiException("No active cart found"));

		CartItem cartItem = cartItemRepository.findById(request.getCartItemId())
				.orElseThrow(() -> new ApiException("Cart item not found"));

		if (!cartItem.getCartId().equals(cart.getCartId())) {
			throw new ApiException("Cart item does not belong to this cart");
		}

		cartItem.setQuantity(request.getQuantity());
		cartItem.setUpdatedAt(LocalDateTime.now());
		cartItemRepository.save(cartItem);

		int total = cartItemRepository
				.findByCartIdAndIsDeletedFalse(cart.getCartId())
				.stream()
				.mapToInt(i -> i.getQuantity() * i.getUnitPrice())
				.sum();

		cart.setTotalAmount(total);
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);

		return ResponseEntity.ok("Cart item updated");

	}

	public ResponseEntity<?> deleteCartItem(Long customerId, Long cartItemId) {

		Cart cart = cartRepository
				.findByCustomerIdAndActiveTrueAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ApiException("No active cart found"));

		CartItem cartItem = cartItemRepository.findById(cartItemId)
				.orElseThrow(() -> new ApiException("Cart item not found"));

		if (!cartItem.getCartId().equals(cart.getCartId())) {
			throw new ApiException("Cart item does not belong to this cart");
		}

		cartItem.setDeleted(true);
		cartItem.setUpdatedAt(LocalDateTime.now());
		cartItemRepository.save(cartItem);

		int total = cartItemRepository
				.findByCartIdAndIsDeletedFalse(cart.getCartId())
				.stream()
				.mapToInt(i -> i.getQuantity() * i.getUnitPrice())
				.sum();

		cart.setTotalAmount(total);
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);
		return ResponseEntity.ok("Cart item deleted");

	}

	public ResponseEntity<?> clearCart(Long customerId) {
		Cart cart = cartRepository.findByCustomerIdAndActiveTrueAndIsDeletedFalse(customerId)
				.orElseThrow(() -> new ApiException("No active cart found"));
		List<CartItem> items = cartItemRepository.findByCartIdAndIsDeletedFalse(cart.getCartId());
		items.forEach(i -> {
			i.setDeleted(true);
			i.setUpdatedAt(LocalDateTime.now());
		});
		cartItemRepository.saveAll(items);
		cart.setTotalAmount(0);
		cart.setUpdatedAt(LocalDateTime.now());
		cartRepository.save(cart);
		return ResponseEntity.ok("Cart cleared");
	}
}