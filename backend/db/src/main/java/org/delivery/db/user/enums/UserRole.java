package org.delivery.db.user.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    USER_ROLE("일반유저"),
    ;

    private final String description;
}
