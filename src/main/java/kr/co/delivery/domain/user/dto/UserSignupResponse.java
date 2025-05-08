package kr.co.delivery.domain.user.dto;

import kr.co.delivery.domain.user.entity.User;

import java.time.LocalDateTime;

public record UserSignupResponse(
        Long id,
        String email,
        String name,
        LocalDateTime createdAt
) {
    public static UserSignupResponse from(User user) {
        return new UserSignupResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt()
        );
    }
}
