package kr.co.delivery.api.user.auth.controller.dto;

import kr.co.delivery.db.domains.user.domain.UserEntity;

public record UserAuthResponse(
        String email,
        String nickname
) {
    public static UserAuthResponse fromEntity(UserEntity user) {
        return new UserAuthResponse(user.getEmail(), user.getNickname());
    }
}
