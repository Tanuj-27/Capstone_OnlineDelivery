package com.ey.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.ey.dto.request.OrderCreateRequest;

import com.ey.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<?> getOrders(@PathVariable Long customerId) {
        return orderService.getOrders(customerId);
    }

    @GetMapping("/customers/{customerId}/orders/{orderId}")
    public ResponseEntity<?> getOrderById(
            @PathVariable Long customerId,
            @PathVariable Long orderId) {
        return orderService.getOrderById(customerId, orderId);
    }

    @PostMapping("/customers/{customerId}/orders/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId) {
        return orderService.cancelOrder(customerId, orderId);
    }

}
 