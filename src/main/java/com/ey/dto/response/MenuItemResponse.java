package com.ey.dto.response;

public class MenuItemResponse {

    private Long menuItemId;

    private Long restaurantId;

    private String name;

    private String description;

    private Integer price;

    public Long getMenuItemId() {

        return menuItemId;

    }

    public void setMenuItemId(Long menuItemId) {

        this.menuItemId = menuItemId;

    }

    public Long getRestaurantId() {

        return restaurantId;

    }

    public void setRestaurantId(Long restaurantId) {

        this.restaurantId = restaurantId;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public Integer getPrice() {

        return price;

    }

    public void setPrice(Integer price) {

        this.price = price;

    }

}
 