package com.ey.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.ey.exception.ApiException;

import com.ey.model.Order;

import com.ey.repository.OrderRepository;

import com.ey.service.OrderService;

@SpringBootTest

class OrderServiceTest {

    @Autowired

    private OrderService orderService;

    @MockBean

    private OrderRepository orderRepository;

    @Test
    void getOrderById_success() {
       Order order = new Order();
       order.setOrderId(1L);
       order.setCustomerId(1L);
       order.setDeleted(false);
       order.setPaymentMethod(com.ey.enums.PaymentMethod.CARD);
       order.setPaymentStatus(com.ey.enums.PaymentStatus.SUCCESS);
       order.setStatus(com.ey.enums.OrderStatus.PLACED);
       order.setTotalAmount(100);
       when(orderRepository.findByOrderIdAndCustomerIdAndIsDeletedFalse(1L, 1L))
               .thenReturn(Optional.of(order));
       Object response = orderService.getOrderById(1L, 1L);
       assertNotNull(response);
    }
    
    @Test

    void getOrderById_failure_orderNotFound() {

        when(orderRepository.findByOrderIdAndCustomerIdAndIsDeletedFalse(1L, 1L))

                .thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> {

            orderService.getOrderById(1L, 1L);

        });

    }

}
 