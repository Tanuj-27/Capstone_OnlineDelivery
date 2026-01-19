package com.ey.service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ey.dto.request.RestaurantCreateRequest;
import com.ey.dto.request.RestaurantUpdateRequest;
import com.ey.dto.response.RestaurantResponse;
import com.ey.exception.ApiException;
import com.ey.mapper.RestaurantMapper;
import com.ey.model.Restaurant;
import com.ey.repository.RestaurantRepository;

@Service
public class RestaurantService {
   private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
   private final RestaurantRepository restaurantRepository;
   public RestaurantService(RestaurantRepository restaurantRepository) {
       this.restaurantRepository = restaurantRepository;
   }
   public ResponseEntity<?> createRestaurant(RestaurantCreateRequest request) {
       logger.info("Create restaurant request received for ownerId: {}", request.getOwnerId());
       Restaurant restaurant = RestaurantMapper.toEntity(request, request.getOwnerId());
       restaurant.setDeleted(false);
       restaurant.setCreatedAt(LocalDateTime.now());
       restaurant.setUpdatedAt(LocalDateTime.now());
       Restaurant saved = restaurantRepository.save(restaurant);
       Map<String, Object> response = new HashMap<>();
       response.put("restaurantId", saved.getRestaurantId());
       response.put("ownerId", saved.getOwnerId());
       response.put("message", "Restaurant created");
       response.put("createdBy", "OWNER");
       response.put("createdAt", LocalDateTime.now());
       logger.info("Restaurant created successfully. restaurantId: {}", saved.getRestaurantId());
       return new ResponseEntity<>(response, HttpStatus.CREATED);
   }
   
   public ResponseEntity<?> getRestaurantsByCity(String city) {
       if (city == null || city.trim().isEmpty()) {
           throw new ApiException("Invalid/missing city");
       }
       logger.info("Get restaurants by city: {}", city);
       List<RestaurantResponse> list = restaurantRepository.findByCityAndIsDeletedFalse(city)
               .stream()
               .map(RestaurantMapper::toResponse)
               .toList();
       return new ResponseEntity<>(list, HttpStatus.OK);
   }
   
   public ResponseEntity<?> getAllRestaurants() {
	   logger.info("Get all restaurants request received");
	   List<RestaurantResponse> list = restaurantRepository.findByIsDeletedFalse()
	           .stream()
	           .map(RestaurantMapper::toResponse)
	           .toList();
	   return new ResponseEntity<>(list, HttpStatus.OK);
	}
   
   public ResponseEntity<?> getRestaurantById(Long id) {
       logger.info("Get restaurant by id: {}", id);
       Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeletedFalse(id)
               .orElseThrow(() -> new ApiException("Restaurant not found"));
       return new ResponseEntity<>(RestaurantMapper.toResponse(restaurant), HttpStatus.OK);
   }
   
   public ResponseEntity<?> getRestaurantsByOwner(Long ownerId) {
       if (ownerId == null) {
           throw new ApiException("Invalid/missing ownerId");
       }
       logger.info("Get restaurants by ownerId: {}", ownerId);
       List<RestaurantResponse> list = restaurantRepository.findByOwnerIdAndIsDeletedFalse(ownerId)
               .stream()
               .map(RestaurantMapper::toResponse)
               .toList();
       return new ResponseEntity<>(list, HttpStatus.OK);
   }
   
   public ResponseEntity<?> updateRestaurant(Long id, RestaurantUpdateRequest request) {
       logger.info("Update restaurant request received. restaurantId: {}, ownerId: {}", id, request.getOwnerId());
       Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeletedFalse(id)
               .orElseThrow(() -> new ApiException("Restaurant not found"));
       restaurant.setName(request.getName());
       restaurant.setAddress(request.getAddress());
       restaurant.setCity(request.getCity());
       restaurant.setUpdatedAt(LocalDateTime.now());
       restaurantRepository.save(restaurant);
       Map<String, Object> response = new HashMap<>();
       response.put("restaurantId", restaurant.getRestaurantId());
       response.put("ownerId", restaurant.getOwnerId());
       response.put("name", restaurant.getName());
       response.put("address", restaurant.getAddress());
       response.put("city", restaurant.getCity());
       response.put("updatedBy", "OWNER");
       response.put("updatedAt", LocalDateTime.now());
       logger.info("Restaurant updated successfully. restaurantId: {}", restaurant.getRestaurantId());
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> deleteRestaurant(Long id) {
       logger.info("Delete (soft) restaurant request received. restaurantId: {}", id);
       Restaurant restaurant = restaurantRepository.findByRestaurantIdAndIsDeletedFalse(id)
               .orElseThrow(() -> new ApiException("Restaurant not found"));
       restaurant.setDeleted(true);
       restaurant.setUpdatedAt(LocalDateTime.now());
       restaurantRepository.save(restaurant);
       Map<String, Object> response = new HashMap<>();
       response.put("message", "Restaurant deleted");
       response.put("updatedBy", "OWNER");
       response.put("updatedAt", LocalDateTime.now());
       logger.info("Restaurant soft deleted successfully. restaurantId: {}", id);
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
}