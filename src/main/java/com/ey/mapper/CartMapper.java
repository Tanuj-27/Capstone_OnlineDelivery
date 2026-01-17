package com.ey.mapper;

import java.util.List;

import com.ey.dto.response.CartItemResponse;

import com.ey.dto.response.CartResponse;

import com.ey.model.Cart;

import com.ey.model.CartItem;

public class CartMapper {

    public static CartItemResponse toItemResponse(CartItem item, String itemName) {

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(item.getCartItemId());
        response.setItemName(itemName);
        response.setQuantity(item.getQuantity());
        response.setUnitPrice(item.getUnitPrice());
        response.setTotalPrice(item.getQuantity() * item.getUnitPrice());

        return response;

    }

    public static CartResponse toCartResponse(Cart cart, Integer totalAmount, List<CartItemResponse> items) {

        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());
        response.setCustomerId(cart.getCustomerId());
        response.setActive(cart.isActive());
        response.setTotalAmount(totalAmount);
        response.setItems(items);

        return response;

    }

}
 