package com.example.delivery.domain.user.dto;

import com.example.delivery.domain.user.entity.User;

public record SignupResponse(
        Long id,
        String email,
        String nickname
) {

    public static SignupResponse from(User user) {
        return new SignupResponse(user.getId(), user.getEmail(), user.getNickname());
    }
}
