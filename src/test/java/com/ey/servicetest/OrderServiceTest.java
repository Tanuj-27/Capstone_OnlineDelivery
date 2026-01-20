package com.ey.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import com.ey.dto.request.CartItemCreateRequest;

import com.ey.dto.request.OrderCreateRequest;

import com.ey.enums.PaymentMethod;

import com.ey.exception.ApiException;

import com.ey.model.Address;

import com.ey.model.Cart;

import com.ey.model.MenuItem;

import com.ey.repository.AddressRepository;

import com.ey.repository.CartRepository;

import com.ey.repository.MenuItemRepository;

import com.ey.service.CartService;

import com.ey.service.OrderService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    private Long customerId;
    private Long addressId;
    private Long menuItemId;

    @BeforeEach
    void setUp() {

        customerId = 15L;
        Address a = new Address();
        a.setCustomerId(customerId);       
        a.setCity("Hyderabad");
        a.setState("TS");
        a.setPincode("500001");
        a.setDefault(false);
        a.setDeleted(false);
        a.setCreatedAt(LocalDateTime.now());
        a.setUpdatedAt(LocalDateTime.now());
        addressId = addressRepository.save(a).getAddressId();

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
        item.setName("Test Pizza");
        item.setDescription("Test Desc");
        item.setPrice(200);
        item.setDeleted(false);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        menuItemId = menuItemRepository.save(item).getMenuItemId();
        CartItemCreateRequest add = new CartItemCreateRequest();
        add.setCustomerId(customerId);
        add.setMenuItemId(menuItemId);
        add.setQuantity(2);

        cartService.addItemToCart(add);

    }

    @Test
    void createOrder_success() {

        OrderCreateRequest req = new OrderCreateRequest();
        req.setCustomerId(customerId);
        req.setAddressId(addressId);
        req.setPaymentMethod(PaymentMethod.CARD);
        req.setScheduledDeliveryTime(LocalDateTime.now().plusHours(1));
        Object response = orderService.createOrder(req).getBody();
        assertNotNull(response);

    }

    @Test
    void createOrder_failure_cartEmpty() {

        Long otherCustomer = 9999L;
        OrderCreateRequest req = new OrderCreateRequest();
        req.setCustomerId(otherCustomer);
        req.setAddressId(addressId);
        req.setPaymentMethod(PaymentMethod.CARD);
        req.setScheduledDeliveryTime(LocalDateTime.now().plusHours(1));
        assertThrows(ApiException.class, () -> orderService.createOrder(req));

    }

}
 