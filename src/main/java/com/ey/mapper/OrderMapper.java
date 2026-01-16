package com.ey.mapper;
import com.ey.dto.response.OrderResponse;
import com.ey.model.Order;

public class OrderMapper {
   public static OrderResponse toResponse(Order order) {
	   
       OrderResponse response = new OrderResponse();
       response.setOrderId(order.getOrderId());
       response.setCustomerId(order.getCustomerId());
       response.setRestaurantId(order.getRestaurantId());
       response.setAddressId(order.getAddressId());
       response.setPaymentMethod(order.getPaymentMethod());
       response.setPaymentStatus(order.getPaymentStatus());
       response.setStatus(order.getStatus());
       response.setDeliveryPartner(order.getDeliveryPartner());
       response.setTotalAmount(order.getTotalAmount());
       response.setScheduledDeliveryTime(order.getScheduledDeliveryTime());
       return response;
       
   }
}