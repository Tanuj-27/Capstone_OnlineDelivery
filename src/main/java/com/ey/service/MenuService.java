package com.ey.service;

import java.time.LocalDateTime;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.ey.dto.request.MenuItemCreateRequest;

import com.ey.dto.request.MenuItemPriceUpdateRequest;

import com.ey.dto.request.MenuItemUpdateRequest;

import com.ey.dto.response.MenuItemResponse;

import com.ey.enums.Role;

import com.ey.exception.ApiException;

import com.ey.mapper.MenuItemMapper;

import com.ey.model.MenuItem;

import com.ey.model.Restaurant;

import com.ey.model.User;

import com.ey.repository.MenuItemRepository;

import com.ey.repository.RestaurantRepository;

import com.ey.repository.UserRepository;

@Service

public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    private final MenuItemRepository menuItemRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public MenuService(

            MenuItemRepository menuItemRepository,

            UserRepository userRepository,

            RestaurantRepository restaurantRepository

    ) {

        this.menuItemRepository = menuItemRepository;

        this.userRepository = userRepository;

        this.restaurantRepository = restaurantRepository;

    }    

    private Restaurant getOwnerRestaurant() {

        String email = SecurityContextHolder.getContext()

                .getAuthentication()

                .getName();

        User owner = userRepository.findByEmailAndIsDeletedFalse(email)

                .orElseThrow(() -> new ApiException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {

            throw new ApiException("Access denied");

        }

        return restaurantRepository.findByOwnerIdAndIsDeletedFalse(owner.getUserId())

                .stream()

                .findFirst()

                .orElseThrow(() -> new ApiException("Restaurant not found for owner"));

    }
   
    // ADD MENU ITEM   

    public ResponseEntity<?> addMenuItem(Long restaurantIdIgnored, MenuItemCreateRequest request) {

        Restaurant restaurant = getOwnerRestaurant();

        Long restaurantId = restaurant.getRestaurantId();

        MenuItem item = MenuItemMapper.toEntity(request, restaurantId);

        item.setDeleted(false);

        item.setCreatedAt(LocalDateTime.now());

        item.setUpdatedAt(LocalDateTime.now());

        MenuItem saved = menuItemRepository.save(item);

        Map<String, Object> response = new HashMap<>();

        response.put("menuItemId", saved.getMenuItemId());

        response.put("restaurantId", restaurantId);

        response.put("message", "Menu item created");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    // GET MENU ITEMS

    public ResponseEntity<?> getMenuItems(Long restaurantIdIgnored) {

        Restaurant restaurant = getOwnerRestaurant();

        List<MenuItemResponse> list = menuItemRepository

                .findByRestaurantIdAndIsDeletedFalse(restaurant.getRestaurantId())

                .stream()

                .map(MenuItemMapper::toResponse)

                .toList();

        return ResponseEntity.ok(list);

    }

    // GET MENU ITEM BY ID


    public ResponseEntity<?> getMenuItemById(Long restaurantIdIgnored, Long itemId) {

        Restaurant restaurant = getOwnerRestaurant();

        MenuItem item = menuItemRepository

                .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(

                        itemId, restaurant.getRestaurantId())

                .orElseThrow(() -> new ApiException("Menu item not found"));

        return ResponseEntity.ok(MenuItemMapper.toResponse(item));

    }

    // UPDATE MENU ITEM


    public ResponseEntity<?> updateMenuItem(Long restaurantIdIgnored, Long itemId,

                                            MenuItemUpdateRequest request) {

        Restaurant restaurant = getOwnerRestaurant();

        MenuItem item = menuItemRepository

                .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(

                        itemId, restaurant.getRestaurantId())

                .orElseThrow(() -> new ApiException("Menu item not found"));

        item.setName(request.getName());

        item.setDescription(request.getDescription());

        item.setPrice(request.getPrice());

        item.setUpdatedAt(LocalDateTime.now());

        menuItemRepository.save(item);

        return ResponseEntity.ok(MenuItemMapper.toResponse(item));

    }

    // UPDATE PRICE

    public ResponseEntity<?> updateMenuItemPrice(Long restaurantIdIgnored, Long itemId,

                                                 MenuItemPriceUpdateRequest request) {

        Restaurant restaurant = getOwnerRestaurant();

        MenuItem item = menuItemRepository

                .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(

                        itemId, restaurant.getRestaurantId())

                .orElseThrow(() -> new ApiException("Menu item not found"));

        item.setPrice(request.getPrice());

        item.setUpdatedAt(LocalDateTime.now());

        menuItemRepository.save(item);

        return ResponseEntity.ok(MenuItemMapper.toResponse(item));

    }

    // DELETE MENU ITEM

    public ResponseEntity<?> deleteMenuItem(Long restaurantIdIgnored, Long itemId) {

        Restaurant restaurant = getOwnerRestaurant();

        MenuItem item = menuItemRepository

                .findByMenuItemIdAndRestaurantIdAndIsDeletedFalse(

                        itemId, restaurant.getRestaurantId())

                .orElseThrow(() -> new ApiException("Menu item not found"));

        item.setDeleted(true);

        item.setUpdatedAt(LocalDateTime.now());

        menuItemRepository.save(item);

        return ResponseEntity.ok(Map.of("message", "Menu item deleted"));

    }

    // SEARCH

    public ResponseEntity<?> searchMenu(Long restaurantIdIgnored, String keyword) {

        Restaurant restaurant = getOwnerRestaurant();

        List<MenuItemResponse> list = menuItemRepository

                .findByRestaurantIdAndNameContainingIgnoreCaseAndIsDeletedFalse(

                        restaurant.getRestaurantId(), keyword)

                .stream()

                .map(MenuItemMapper::toResponse)

                .toList();

        return ResponseEntity.ok(list);

    }

    // FILTER PRICE
    public ResponseEntity<?> filterByPriceRange(Long restaurantIdIgnored, Integer min, Integer max) {

        Restaurant restaurant = getOwnerRestaurant();

        List<MenuItemResponse> list = menuItemRepository

                .findByRestaurantIdAndPriceBetweenAndIsDeletedFalse(

                        restaurant.getRestaurantId(), min, max)

                .stream()

                .map(MenuItemMapper::toResponse)

                .toList();

        return ResponseEntity.ok(list);

    }

}
 