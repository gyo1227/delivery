package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {

    OPEN("오픈"),
    CLOSE("준비중"),
    ;

    private final String description;

}
