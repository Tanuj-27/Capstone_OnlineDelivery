package com.ey.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.RestaurantCreateRequest;
import com.ey.dto.request.RestaurantUpdateRequest;
import com.ey.service.RestaurantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RestaurantController {
  
	@Autowired
   private RestaurantService restaurantService;
   
	@PostMapping("/restaurants")
   public ResponseEntity<?> createRestaurant(
           @Valid @RequestBody RestaurantCreateRequest request) {
       return restaurantService.createRestaurant(request);
   }
	
   @PutMapping("/restaurants/{restaurantId}")
   public ResponseEntity<?> updateRestaurant(
           @PathVariable Long restaurantId,
           @Valid @RequestBody RestaurantUpdateRequest request) {
       return restaurantService.updateRestaurant(restaurantId, request);
   }
   
   @DeleteMapping("/restaurants/{restaurantId}")
   public ResponseEntity<?> deleteRestaurant(
           @PathVariable Long restaurantId) {
       return restaurantService.deleteRestaurant(restaurantId);
   }
   
   @GetMapping("/restaurants/{restaurantId}")
   public ResponseEntity<?> getRestaurantById(
           @PathVariable Long restaurantId) {
       return restaurantService.getRestaurantById(restaurantId);
   }
   
   @GetMapping("/restaurants/all")
   public ResponseEntity<?> getAllRestaurants() {
       return restaurantService.getAllRestaurants();
   }
   
   @GetMapping("/restaurants/city/{city}")
   public ResponseEntity<?> getRestaurantsByCity(
           @PathVariable String city) {
       return restaurantService.getRestaurantsByCity(city);
   }
   
   @GetMapping("/restaurants/owner/{ownerId}")
   public ResponseEntity<?> getRestaurantsByOwner(
           @PathVariable Long ownerId) {
       return restaurantService.getRestaurantsByOwner(ownerId);
   }
}