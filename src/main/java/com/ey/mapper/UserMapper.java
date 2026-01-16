package com.ey.mapper;

import com.ey.dto.request.RegisterRequest;

import com.ey.dto.response.UserResponse;

import com.ey.model.User;

public class UserMapper {

    public static User toEntity(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return user;

    }

    public static UserResponse toResponse(User user) {

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        response.setPhone(user.getPhone());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        return response;

    }

}
 