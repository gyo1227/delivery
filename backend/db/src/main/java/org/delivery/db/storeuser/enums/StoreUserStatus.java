package org.delivery.db.storeuser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserStatus {

    REGISTERED("가입"),
    UNREGISTERED("탈퇴"),
    ;

    private final String status;

}
