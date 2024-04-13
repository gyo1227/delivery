package org.delivery.db.storemenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreMenuStatus {

    SALE("판매"),
    SOLD_OUT("매진"),
    ;

    private final String description;
}
