package com.ey.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.ey.dto.request.MenuItemCreateRequest;

import com.ey.dto.request.MenuItemPriceUpdateRequest;

import com.ey.dto.request.MenuItemUpdateRequest;

import com.ey.service.MenuService;

import jakarta.validation.Valid;

import jakarta.validation.constraints.Min;

@RestController

@RequestMapping("/api")

public class MenuController {

    @Autowired

    private MenuService menuService;

    @PostMapping("/restaurants/{restaurantId}/menu")

    public ResponseEntity<?> addMenuItem(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @Valid @RequestBody MenuItemCreateRequest request) {

        return menuService.addMenuItem(restaurantId, request);

    }

    @GetMapping("/restaurants/{restaurantId}/menu")

    public ResponseEntity<?> getMenuItems(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId) {

        return menuService.getMenuItems(restaurantId);

    }

    @GetMapping("/restaurants/{restaurantId}/menu/search/{keyword}")

    public ResponseEntity<?> searchMenu(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable String keyword) {

        return menuService.searchMenu(restaurantId, keyword);

    }

    @GetMapping("/restaurants/{restaurantId}/menu/price-range/{min}/{max}")

    public ResponseEntity<?> filterByPriceRange(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable Integer min,

            @PathVariable Integer max) {

        return menuService.filterByPriceRange(restaurantId, min, max);

    }

    @GetMapping("/restaurants/{restaurantId}/menu/{itemId}")

    public ResponseEntity<?> getMenuItemById(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable @Min(value = 1, message = "itemId must be valid") Long itemId) {

        return menuService.getMenuItemById(restaurantId, itemId);

    }

    @PutMapping("/restaurants/{restaurantId}/menu/{itemId}")

    public ResponseEntity<?> updateMenuItem(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable @Min(value = 1, message = "itemId must be valid") Long itemId,

            @Valid @RequestBody MenuItemUpdateRequest request) {

        return menuService.updateMenuItem(restaurantId, itemId, request);

    }

    @PutMapping("/restaurants/{restaurantId}/menu/{itemId}/price")

    public ResponseEntity<?> updateMenuItemPrice(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable @Min(value = 1, message = "itemId must be valid") Long itemId,

            @Valid @RequestBody MenuItemPriceUpdateRequest request) {

        return menuService.updateMenuItemPrice(restaurantId, itemId, request);

    }

    @DeleteMapping("/restaurants/{restaurantId}/menu/{itemId}")

    public ResponseEntity<?> deleteMenuItem(

            @PathVariable @Min(value = 1, message = "restaurantId must be valid") Long restaurantId,

            @PathVariable @Min(value = 1, message = "itemId must be valid") Long itemId) {

        return menuService.deleteMenuItem(restaurantId, itemId);

    }

}
 