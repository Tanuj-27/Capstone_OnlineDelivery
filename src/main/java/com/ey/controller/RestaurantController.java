package com.ey.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.RestaurantCreateRequest;
import com.ey.dto.request.RestaurantUpdateRequest;
import com.ey.service.RestaurantService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RestaurantController {
   private final RestaurantService restaurantService;
   public RestaurantController(RestaurantService restaurantService) {
       this.restaurantService = restaurantService;
   }
   
   // CREATE restaurant (OWNER)
   @PostMapping("/restaurants")
   public ResponseEntity<?> createRestaurant(
           @Valid @RequestBody RestaurantCreateRequest request) {
       return restaurantService.createRestaurant(request);
   }
   
   // GET ALL restaurants
   @GetMapping("/restaurants/all")
   public ResponseEntity<?> getAllRestaurants() {
       return restaurantService.getAllRestaurants();
   }
   
   // GET restaurants by city OR owner
   @GetMapping("/restaurants")
   public ResponseEntity<?> getRestaurants(
           @RequestParam(required = false) String city,
           @RequestParam(required = false) Long ownerId) {
       if (city != null) {
           return restaurantService.getRestaurantsByCity(city);
       }
       if (ownerId != null) {
           return restaurantService.getRestaurantsByOwner(ownerId);
       }
       return ResponseEntity
               .badRequest()
               .body(java.util.Map.of("error", "Invalid/missing city or ownerId"));
   }
   
   // GET restaurant by ID
   @GetMapping("/restaurants/{id}")
   public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
       return restaurantService.getRestaurantById(id);
   }
   
   // UPDATE restaurant (OWNER)
   @PutMapping("/restaurants/{id}")
   public ResponseEntity<?> updateRestaurant(
           @PathVariable Long id,
           @Valid @RequestBody RestaurantUpdateRequest request) {
       return restaurantService.updateRestaurant(id, request);
   }
   
   // DELETE restaurant (SOFT DELETE)
   @DeleteMapping("/restaurants/{id}")
   public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
       return restaurantService.deleteRestaurant(id);
   }
}