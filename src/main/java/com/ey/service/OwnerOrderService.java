package com.ey.service;

import java.time.LocalDateTime;

import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.ey.dto.request.AssignDeliveryPartnerRequest;

import com.ey.dto.response.OwnerOrderSummaryResponse;

import com.ey.enums.OrderStatus;

import com.ey.exception.ApiException;

import com.ey.model.Order;

import com.ey.model.Restaurant;

import com.ey.model.User;

import com.ey.repository.OrderRepository;

import com.ey.repository.RestaurantRepository;

import com.ey.repository.UserRepository;

@Service
public class OwnerOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OwnerOrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> getOrdersByRestaurant(Long ignoredRestaurantId) {
        logger.info("Fetching orders for logged-in owner");        

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException("Owner not found"));

        

        Restaurant restaurant = restaurantRepository
                .findByOwnerIdAndIsDeletedFalse(owner.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ApiException("Restaurant not found for owner"));

        Long restaurantId = restaurant.getRestaurantId();

        

        List<OwnerOrderSummaryResponse> list = orderRepository
                .findByRestaurantIdAndIsDeletedFalse(restaurantId)
                .stream()
                .map(o -> {
                    OwnerOrderSummaryResponse r = new OwnerOrderSummaryResponse();
                    r.setOrderId(o.getOrderId());
                    r.setStatus(o.getStatus());
                    r.setTotalAmount(o.getTotalAmount());
                    return r;
                })

                .toList();

        return ResponseEntity.ok(list);

    }

    public ResponseEntity<?> moveToNextStatus(Long orderId) {

        logger.info("Moving order {} to next status", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));
        OrderStatus current = order.getStatus();
        OrderStatus next = getNextStatus(current);

        if (next == null) {
            throw new ApiException("Invalid transition");
        }

        order.setStatus(next);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        OwnerOrderSummaryResponse response = new OwnerOrderSummaryResponse();
        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> assignDeliveryPartner(Long orderId, AssignDeliveryPartnerRequest request) {

        logger.info("Assigning delivery partner for order {}", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));

        if (order.getStatus() != OrderStatus.OUT_FOR_DELIVERY) {
            throw new ApiException("Status not OUT_FOR_DELIVERY");
        }

        order.setDeliveryPartner(request.getPartner());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        OwnerOrderSummaryResponse response = new OwnerOrderSummaryResponse();

        response.setOrderId(order.getOrderId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());

        return ResponseEntity.ok(response);

    }

    private OrderStatus getNextStatus(OrderStatus current) {

        if (current == null) return null;
        if (current == OrderStatus.PLACED) return OrderStatus.CONFIRMED;
        if (current == OrderStatus.CONFIRMED) return OrderStatus.PREPARING;
        if (current == OrderStatus.PREPARING) return OrderStatus.OUT_FOR_DELIVERY;
        if (current == OrderStatus.OUT_FOR_DELIVERY) return OrderStatus.DELIVERED;

        return null;

    }

}
 