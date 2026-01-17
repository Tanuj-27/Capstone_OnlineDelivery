package com.ey.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import com.ey.dto.response.PaymentResponse;

import com.ey.enums.OrderStatus;

import com.ey.enums.PaymentStatus;

import com.ey.exception.ApiException;

import com.ey.model.Order;

import com.ey.repository.OrderRepository;

@Service
public class PaymentService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final OrderRepository orderRepository;

    public PaymentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> makePayment(Long orderId) {
        logger.info("Processing payment for orderId {}", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new ApiException("Payment already processed or order not eligible");
        }

        order.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(System.currentTimeMillis());
        response.setOrderId(order.getOrderId());
        response.setPaymentStatus(order.getPaymentStatus().name());
        response.setAmount(order.getTotalAmount());
        response.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> getPaymentByOrder(Long orderId) {
        logger.info("Fetching payment for orderId {}", orderId);

        Order order = orderRepository.findByOrderIdAndIsDeletedFalse(orderId)
                .orElseThrow(() -> new ApiException("Order not found"));

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(System.currentTimeMillis());
        response.setOrderId(order.getOrderId());
        response.setPaymentStatus(order.getPaymentStatus().name());
        response.setAmount(order.getTotalAmount());
        response.setCreatedAt(order.getUpdatedAt());
        
        return ResponseEntity.ok(response);

    }

}
 