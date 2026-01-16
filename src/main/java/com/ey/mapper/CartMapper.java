package com.ey.mapper;

import java.util.ArrayList;

import java.util.List;

import com.ey.dto.response.CartItemResponse;

import com.ey.dto.response.CartResponse;

import com.ey.model.Cart;

import com.ey.model.CartItem;

public class CartMapper {

    public static CartResponse toResponse(Cart cart, List<CartItem> cartItems) {

        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());
        response.setCustomerId(cart.getCustomerId());
        response.setActive(cart.isActive());
        response.setTotalAmount(cart.getTotalAmount());
        List<CartItemResponse> itemResponses = new ArrayList<>();

        for (CartItem item : cartItems) {
            CartItemResponse itemResponse = new CartItemResponse();
            itemResponse.setCartItemId(item.getCartItemId());
            itemResponse.setMenuItemId(item.getMenuItemId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setUnitPrice(item.getUnitPrice());
            itemResponses.add(itemResponse);
        }
        response.setItems(itemResponses);

        return response;

    }

}
 