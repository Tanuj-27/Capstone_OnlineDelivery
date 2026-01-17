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
       response.setPaymentMethod(order.getPaymentMethod().name());
       response.setPaymentStatus(order.getPaymentStatus().name());
       response.setStatus(order.getStatus().name());
       response.setDeliveryPartner(order.getDeliveryPartner());
       response.setTotalAmount(order.getTotalAmount());
       response.setScheduledDeliveryTime(order.getScheduledDeliveryTime());
       response.setCreatedAt(order.getCreatedAt());
      
       return response;
   }
}