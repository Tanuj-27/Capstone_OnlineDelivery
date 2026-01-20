package com.ey.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import com.ey.dto.request.CartItemCreateRequest;

import com.ey.exception.ApiException;

import com.ey.model.Cart;

import com.ey.model.MenuItem;

import com.ey.repository.CartRepository;

import com.ey.repository.MenuItemRepository;

import com.ey.service.CartService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    private Long customerId;
    private Long menuItemId;

    @BeforeEach
    void setUp() {

        customerId = 15L;
        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setActive(true);
        cart.setTotalAmount(0);
        cart.setDeleted(false);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        MenuItem item = new MenuItem();
        item.setRestaurantId(16L);
        item.setName("Test Item");
        item.setDescription("Test Desc");
        item.setPrice(120);
        item.setDeleted(false);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        menuItemId = menuItemRepository.save(item).getMenuItemId();

    }

    @Test
    void addItemToCart_success() {

        CartItemCreateRequest req = new CartItemCreateRequest();
        req.setCustomerId(customerId);
        req.setMenuItemId(menuItemId);
        req.setQuantity(2);
        Object response = cartService.addItemToCart(req).getBody();
        assertNotNull(response);

    }

    @Test
    void addItemToCart_failure_menuItemNotFound() {

        CartItemCreateRequest req = new CartItemCreateRequest();
        req.setCustomerId(customerId);
        req.setMenuItemId(999999L);
        req.setQuantity(1);
        assertThrows(ApiException.class, () -> cartService.addItemToCart(req));

    }

}
 