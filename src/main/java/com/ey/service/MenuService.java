package com.ey.service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ey.dto.request.MenuItemCreateRequest;
import com.ey.dto.request.MenuItemPriceUpdateRequest;
import com.ey.dto.request.MenuItemUpdateRequest;
import com.ey.dto.response.MenuItemResponse;
import com.ey.exception.ApiException;
import com.ey.mapper.MenuItemMapper;
import com.ey.model.MenuItem;
import com.ey.repository.MenuItemRepository;

@Service
public class MenuService {
   private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
   
   @Autowired
   private MenuItemRepository menuItemRepository;
   
   public ResponseEntity<?> addMenuItem(Long restaurantId, MenuItemCreateRequest request) {
       logger.info("Add menu item request for restaurantId: {}", restaurantId);
       
       MenuItem item = MenuItemMapper.toEntity(request, restaurantId);
       item.setDeleted(false);
       item.setCreatedAt(LocalDateTime.now());
       item.setUpdatedAt(LocalDateTime.now());
       MenuItem saved = menuItemRepository.save(item);
      
       Map<String, Object> response = new HashMap<>();
       response.put("menuItemId", saved.getMenuItemId());
       response.put("restaurantId", restaurantId);
       response.put("message", "Menu item created");
       response.put("createdBy", "OWNER");
       response.put("createdAt", LocalDateTime.now());
       
       return new ResponseEntity<>(response, HttpStatus.CREATED);
   }
   
   public ResponseEntity<?> getMenuItems(Long restaurantId) {
       logger.info("Get menu items for restaurantId: {}", restaurantId);
       
       List<MenuItemResponse> list = menuItemRepository
               .findByRestaurantIdAndIsDeletedFalse(restaurantId)
               .stream()
               .map(MenuItemMapper::toResponse)
               .toList();
       return new ResponseEntity<>(list, HttpStatus.OK);
   }
   
   public ResponseEntity<?> getMenuItemById(Long restaurantId, Long itemId) {
       logger.info("Get menu item {} for restaurant {}", itemId, restaurantId);
       MenuItem item = menuItemRepository
               .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(itemId, restaurantId)
               .orElseThrow(() -> new ApiException("Menu item not found"));
       return new ResponseEntity<>(MenuItemMapper.toResponse(item), HttpStatus.OK);
   }
   
   public ResponseEntity<?> updateMenuItem(Long restaurantId, Long itemId, MenuItemUpdateRequest request) {
       logger.info("Update menu item {} for restaurant {}", itemId, restaurantId);
       MenuItem item = menuItemRepository
               .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(itemId, restaurantId)
               .orElseThrow(() -> new ApiException("Menu item not found"));
       item.setName(request.getName());
       item.setDescription(request.getDescription());
       item.setPrice(request.getPrice());
       item.setUpdatedAt(LocalDateTime.now());
       menuItemRepository.save(item);
       return new ResponseEntity<>(MenuItemMapper.toResponse(item), HttpStatus.OK);
   }
   
   public ResponseEntity<?> updateMenuItemPrice(Long restaurantId, Long itemId,
           MenuItemPriceUpdateRequest request) {
       logger.info("Update price for menu item {} in restaurant {}", itemId, restaurantId);
       MenuItem item = menuItemRepository
               .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(itemId, restaurantId)
               .orElseThrow(() -> new ApiException("Menu item not found"));
       item.setPrice(request.getPrice());
       item.setUpdatedAt(LocalDateTime.now());
       menuItemRepository.save(item);
       return new ResponseEntity<>(MenuItemMapper.toResponse(item), HttpStatus.OK);
   }
   
   public ResponseEntity<?> deleteMenuItem(Long restaurantId, Long itemId) {
       logger.info("Soft delete menu item {} in restaurant {}", itemId, restaurantId);
       MenuItem item = menuItemRepository
               .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(itemId, restaurantId)
               .orElseThrow(() -> new ApiException("Menu item not found"));
       item.setDeleted(true);
       item.setUpdatedAt(LocalDateTime.now());
       menuItemRepository.save(item);
      
       Map<String, Object> response = new HashMap<>();
       response.put("message", "Menu item deleted");
       response.put("updatedBy", "OWNER");
       response.put("updatedAt", LocalDateTime.now());
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
   public ResponseEntity<?> searchMenu(Long restaurantId, String query) {
       logger.info("Search menu items in restaurant {} with query {}", restaurantId, query);
       List<MenuItemResponse> list = menuItemRepository
               .findByRestaurantIdAndNameContainingIgnoreCaseAndIsDeletedFalse(restaurantId, query)
               .stream()
               .map(MenuItemMapper::toResponse)
               .toList();
       return new ResponseEntity<>(list, HttpStatus.OK);
   }
   
   public ResponseEntity<?> filterByPriceRange(Long restaurantId, Integer min, Integer max) {
       logger.info("Filter menu items in restaurant {} between {} and {}", restaurantId, min, max);
       List<MenuItemResponse> list = menuItemRepository
               .findByRestaurantIdAndPriceBetweenAndIsDeletedFalse(restaurantId, min, max)
               .stream()
               .map(MenuItemMapper::toResponse)
               .toList();
       return new ResponseEntity<>(list, HttpStatus.OK);
   }
}