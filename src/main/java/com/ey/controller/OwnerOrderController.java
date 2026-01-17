package com.ey.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.AssignDeliveryPartnerRequest;
import com.ey.service.OwnerOrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/owner")
public class OwnerOrderController {
   
	@Autowired
   private OwnerOrderService ownerOrderService;
  
	@GetMapping("/restaurants/{restaurantId}/orders")
   public ResponseEntity<?> getOrdersByRestaurant(
           @PathVariable Long restaurantId) {
       return ownerOrderService.getOrdersByRestaurant(restaurantId);
   }
	
   @PutMapping("/orders/{orderId}/next-status")
   public ResponseEntity<?> moveToNextStatus(
           @PathVariable Long orderId) {
       return ownerOrderService.moveToNextStatus(orderId);
   }
   
   @PutMapping("/orders/{orderId}/delivery-partner")
   public ResponseEntity<?> assignDeliveryPartner(
           @PathVariable Long orderId,
           @Valid @RequestBody AssignDeliveryPartnerRequest request) {
       return ownerOrderService.assignDeliveryPartner(orderId, request);
   }
}