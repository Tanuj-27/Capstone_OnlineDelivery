package com.ey.dto.response;

import java.time.LocalDateTime;

import com.ey.enums.OrderStatus;

import com.ey.enums.PaymentMethod;

import com.ey.enums.PaymentStatus;

public class OrderResponse {

    private Long orderId;
    private Long customerId;
    private Long restaurantId;
    private Long addressId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private OrderStatus status;
    private String deliveryPartner;
    private int totalAmount;
    private LocalDateTime scheduledDeliveryTime;

    public Long getOrderId() {

        return orderId;

    }

    public void setOrderId(Long orderId) {

        this.orderId = orderId;

    }

    public Long getCustomerId() {

        return customerId;

    }

    public void setCustomerId(Long customerId) {

        this.customerId = customerId;

    }

    public Long getRestaurantId() {

        return restaurantId;

    }

    public void setRestaurantId(Long restaurantId) {

        this.restaurantId = restaurantId;

    }

    public Long getAddressId() {

        return addressId;

    }

    public void setAddressId(Long addressId) {

        this.addressId = addressId;

    }

    public PaymentMethod getPaymentMethod() {

        return paymentMethod;

    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {

        this.paymentMethod = paymentMethod;

    }

    public PaymentStatus getPaymentStatus() {

        return paymentStatus;

    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {

        this.paymentStatus = paymentStatus;

    }

    public OrderStatus getStatus() {

        return status;

    }

    public void setStatus(OrderStatus status) {

        this.status = status;

    }

    public String getDeliveryPartner() {

        return deliveryPartner;

    }

    public void setDeliveryPartner(String deliveryPartner) {

        this.deliveryPartner = deliveryPartner;

    }

    public int getTotalAmount() {

        return totalAmount;

    }

    public void setTotalAmount(int totalAmount) {

        this.totalAmount = totalAmount;

    }

    public LocalDateTime getScheduledDeliveryTime() {

        return scheduledDeliveryTime;

    }

    public void setScheduledDeliveryTime(LocalDateTime scheduledDeliveryTime) {

        this.scheduledDeliveryTime = scheduledDeliveryTime;

    }

}
 