package com.ey.mapper;
import com.ey.dto.request.MenuItemCreateRequest;
import com.ey.dto.response.MenuItemResponse;
import com.ey.model.MenuItem;

public class MenuItemMapper {
   public static MenuItem toEntity(MenuItemCreateRequest request, Long restaurantId) {
       MenuItem item = new MenuItem();
       item.setRestaurantId(restaurantId);
       item.setName(request.getName());
       item.setDescription(request.getDescription());
       item.setPrice(request.getPrice());
       return item;
   }
   
   public static MenuItemResponse toResponse(MenuItem item) {
       MenuItemResponse response = new MenuItemResponse();
       response.setMenuItemId(item.getMenuItemId());
       response.setRestaurantId(item.getRestaurantId());
       response.setName(item.getName());
       response.setDescription(item.getDescription());
       response.setPrice(item.getPrice());
       return response;
   }
}