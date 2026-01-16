package com.ey.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ey.dto.request.MenuItemCreateRequest;
import com.ey.dto.request.MenuItemPriceUpdateRequest;
import com.ey.dto.request.MenuItemUpdateRequest;
import com.ey.service.MenuService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class MenuController {
   @Autowired
   private MenuService menuService;
   
   @PostMapping("/restaurants/{restaurantId}/menu")
   public ResponseEntity<?> addMenuItem(
           @PathVariable Long restaurantId,
           @Valid @RequestBody MenuItemCreateRequest request) {
       return menuService.addMenuItem(restaurantId, request);
   }
   
   @GetMapping("/restaurants/{restaurantId}/menu")
   public ResponseEntity<?> getMenuItems(@PathVariable Long restaurantId) {
       return menuService.getMenuItems(restaurantId);
   }
   
   @GetMapping("/restaurants/{restaurantId}/menu/{itemId}")
   public ResponseEntity<?> getMenuItemById(
           @PathVariable Long restaurantId,
           @PathVariable Long itemId) {
       return menuService.getMenuItemById(restaurantId, itemId);
   }
   
   @PutMapping("/restaurants/{restaurantId}/menu/{itemId}")
   public ResponseEntity<?> updateMenuItem(
           @PathVariable Long restaurantId,
           @PathVariable Long itemId,
           @Valid @RequestBody MenuItemUpdateRequest request) {
       return menuService.updateMenuItem(restaurantId, itemId, request);
   }
   
   @PutMapping("/restaurants/{restaurantId}/menu/{itemId}/price")
   public ResponseEntity<?> updateMenuItemPrice(
           @PathVariable Long restaurantId,
           @PathVariable Long itemId,
           @Valid @RequestBody MenuItemPriceUpdateRequest request) {
       return menuService.updateMenuItemPrice(restaurantId, itemId, request);
   }
   
   @DeleteMapping("/restaurants/{restaurantId}/menu/{itemId}")
   public ResponseEntity<?> deleteMenuItem(
           @PathVariable Long restaurantId,
           @PathVariable Long itemId) {
       return menuService.deleteMenuItem(restaurantId, itemId);
   }
   
   @GetMapping("/restaurants/{restaurantId}/menu/search")
   public ResponseEntity<?> searchMenu(
           @PathVariable Long restaurantId,
           @RequestParam("q") String q) {
       return menuService.searchMenu(restaurantId, q);
   }
   
   @GetMapping("/restaurants/{restaurantId}/menu/price-range")
   public ResponseEntity<?> filterByPriceRange(
           @PathVariable Long restaurantId,
           @RequestParam Integer min,
           @RequestParam Integer max) {
       return menuService.filterByPriceRange(restaurantId, min, max);
   }
}