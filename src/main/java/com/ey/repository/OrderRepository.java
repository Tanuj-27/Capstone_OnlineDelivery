package com.ey.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ey.model.Order;

import com.ey.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerIdAndIsDeletedFalse(Long customerId);

    Optional<Order> findByOrderIdAndCustomerIdAndIsDeletedFalse(
            Long orderId, Long customerId);

    List<Order> findByRestaurantIdAndIsDeletedFalse(Long restaurantId);

    Optional<Order> findByOrderIdAndIsDeletedFalse(Long orderId);

    List<Order> findByRestaurantIdAndStatusAndIsDeletedFalse(
            Long restaurantId, OrderStatus status);

}
 