package com.ey.service;

import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.ey.dto.request.AssignDeliveryPartnerRequest;

import com.ey.dto.response.OrderResponse;

import com.ey.enums.OrderStatus;

import com.ey.exception.ApiException;

import com.ey.mapper.OrderMapper;

import com.ey.model.Order;

import com.ey.repository.OrderRepository;

@Service

public class OwnerOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OwnerOrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<?> getOrdersByRestaurant(Long restaurantId) {
        logger.info("Fetching orders for restaurantId {}", restaurantId);

        List<OrderResponse> responses = orderRepository
                .findByRestaurantIdAndIsDeletedFalse(restaurantId)
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);

    }

    public ResponseEntity<?> moveToNextStatus(Long orderId) {
        logger.info("Moving order {} to next status", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));

        OrderStatus current = order.getStatus();

        if (current == OrderStatus.PLACED) {
            order.setStatus(OrderStatus.CONFIRMED);

        } else if (current == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.PREPARING);

        } else if (current == OrderStatus.PREPARING) {
            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);

        } else if (current == OrderStatus.OUT_FOR_DELIVERY) {
            order.setStatus(OrderStatus.DELIVERED);

        } else {
            throw new ApiException("Invalid status transition");
        }

        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return ResponseEntity.ok(OrderMapper.toResponse(order));

    }

    public ResponseEntity<?> assignDeliveryPartner(

            Long orderId,
            AssignDeliveryPartnerRequest request) {
        logger.info("Assigning delivery partner for order {}", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));

        if (order.getStatus() != OrderStatus.OUT_FOR_DELIVERY) {
            throw new ApiException("Order is not out for delivery");
        }

        order.setDeliveryPartner(request.getPartner());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        return ResponseEntity.ok(OrderMapper.toResponse(order));

    }

}

 