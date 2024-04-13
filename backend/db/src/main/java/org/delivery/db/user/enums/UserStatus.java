package org.delivery.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {

    REGISTERED("가입유저"),
    UNREGISTERED("탈퇴유저"),
    ;

    private final String description;
}
