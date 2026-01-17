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

import com.ey.enums.PaymentMethod;

import com.ey.enums.PaymentStatus;

import com.ey.exception.ApiException;

import com.ey.mapper.OrderMapper;

import com.ey.model.Cart;

import com.ey.model.CartItem;

import com.ey.model.MenuItem;

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
        logger.info("Creating order for customerId {}", request.getCustomerId());

        Cart cart = cartRepository.findByCustomerIdAndActiveTrue(request.getCustomerId())
                .orElseThrow(() -> new ApiException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());

        if (cartItems.isEmpty()) {
            throw new ApiException("Cart is empty");
        }

        addressRepository
                .findByAddressIdAndCustomerIdAndIsDeletedFalse(
                        request.getAddressId(), request.getCustomerId())
                .orElseThrow(() -> new ApiException("Invalid address"));

        MenuItem firstMenuItem = menuItemRepository
                .findByMenuItemIdAndIsDeletedFalse(cartItems.get(0).getMenuItemId())
                .orElseThrow(() -> new ApiException("Menu item not found"));

        Long restaurantId = firstMenuItem.getRestaurantId();

        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setRestaurantId(restaurantId);
        order.setAddressId(request.getAddressId());
        order.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));       
        order.setStatus(OrderStatus.PLACED);
        order.setTotalAmount(cart.getTotalAmount());
        order.setScheduledDeliveryTime(request.getScheduledDeliveryTime());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setDeleted(false);
        
        orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setMenuItemId(item.getMenuItemId());
            orderItem.setQuantity(item.getQuantity());

            orderItemRepository.save(orderItem);

        }

        cartItemRepository.deleteAll(cartItems);

        cart.setActive(false);
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);

        OrderResponse response = OrderMapper.toResponse(order);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

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

 