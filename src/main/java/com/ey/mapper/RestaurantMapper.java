package com.ey.mapper;

import com.ey.dto.request.RestaurantCreateRequest;

import com.ey.dto.response.RestaurantResponse;

import com.ey.model.Restaurant;

public class RestaurantMapper {

    public static Restaurant toEntity(RestaurantCreateRequest request, Long ownerId) {

        Restaurant restaurant = new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setCity(request.getCity());
        restaurant.setOwnerId(ownerId);

        return restaurant;

    }

    public static RestaurantResponse toResponse(Restaurant restaurant) {

        RestaurantResponse response = new RestaurantResponse();
        response.setRestaurantId(restaurant.getRestaurantId());
        response.setOwnerId(restaurant.getOwnerId());
        response.setName(restaurant.getName());
        response.setAddress(restaurant.getAddress());
        response.setCity(restaurant.getCity());

        return response;

    }

}
 