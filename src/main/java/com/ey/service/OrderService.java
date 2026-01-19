package com.ey.service;

import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.ey.dto.request.OrderCreateRequest;

import com.ey.dto.response.OrderResponse;

import com.ey.enums.OrderStatus;

import com.ey.enums.PaymentStatus;

import com.ey.exception.ApiException;

import com.ey.mapper.OrderMapper;

import com.ey.model.Cart;

import com.ey.model.CartItem;

import com.ey.model.Order;

import com.ey.model.OrderItem;

import com.ey.repository.AddressRepository;

import com.ey.repository.CartItemRepository;

import com.ey.repository.CartRepository;

import com.ey.repository.MenuItemRepository;

import com.ey.repository.OrderItemRepository;

import com.ey.repository.OrderRepository;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public ResponseEntity<?> createOrder(OrderCreateRequest request) {

        Cart cart = cartRepository
                .findByCustomerIdAndActiveTrueAndIsDeletedFalse(request.getCustomerId())
                .orElseThrow(() -> new ApiException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findByCartIdAndIsDeletedFalse(cart.getCartId());

        if (cartItems.isEmpty()) {
            throw new ApiException("Cart is empty");
        }

        boolean addressExists = addressRepository.findById(request.getAddressId()).isPresent();

        if (!addressExists) {
            throw new ApiException("Invalid address");
        }

        Long restaurantId = menuItemRepository
                .findByMenuItemIdAndIsDeletedFalse(cartItems.get(0).getMenuItemId())
                .orElseThrow(() -> new ApiException("Menu item not found"))
                .getRestaurantId();

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());

        order.setRestaurantId(restaurantId);

        order.setAddressId(request.getAddressId());

        order.setPaymentMethod(request.getPaymentMethod());

        order.setPaymentStatus(PaymentStatus.SUCCESS);

        order.setStatus(OrderStatus.PLACED);

        order.setDeliveryPartner(null);

        order.setTotalAmount(cart.getTotalAmount());

        order.setScheduledDeliveryTime(request.getScheduledDeliveryTime());

        order.setDeleted(false);

        order.setCreatedAt(LocalDateTime.now());

        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        for (CartItem item : cartItems) {

            OrderItem oi = new OrderItem();

            oi.setOrderId(order.getOrderId());

            oi.setMenuItemId(item.getMenuItemId());

            oi.setQuantity(item.getQuantity());

            oi.setUnitPrice(item.getUnitPrice());

            oi.setDeleted(false);

            oi.setCreatedAt(LocalDateTime.now());

            oi.setUpdatedAt(LocalDateTime.now());

            orderItemRepository.save(oi);

        }

        cart.setActive(false);

        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);

        return ResponseEntity.ok("Order placed successfully");

    }

    public ResponseEntity<?> getOrders(Long customerId) {

        logger.info("Fetching orders for customerId {}", customerId);

        List<OrderResponse> responses = orderRepository

                .findByCustomerIdAndIsDeletedFalse(customerId)

                .stream()

                .map(OrderMapper::toResponse)

                .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);

    }

    public ResponseEntity<?> getOrderById(Long customerId, Long orderId) {

        logger.info("Fetching order {} for customer {}", orderId, customerId);

        Order order = orderRepository

                .findByOrderIdAndCustomerIdAndIsDeletedFalse(orderId, customerId)

                .orElseThrow(() -> new ApiException("Order not found"));

        return new ResponseEntity<>(OrderMapper.toResponse(order), HttpStatus.OK);

    }

    public ResponseEntity<?> cancelOrder(Long customerId, Long orderId) {

        logger.info("Cancelling order {} for customer {}", orderId, customerId);

        Order order = orderRepository

                .findByOrderIdAndCustomerIdAndIsDeletedFalse(orderId, customerId)

                .orElseThrow(() -> new ApiException("Order not found"));

        if (order.getStatus() != OrderStatus.PLACED) {

            throw new ApiException("Order cannot be cancelled");

        }

        order.setStatus(OrderStatus.CANCELLED);

        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);

        return new ResponseEntity<>("Order cancelled", HttpStatus.OK);

    }

}
 