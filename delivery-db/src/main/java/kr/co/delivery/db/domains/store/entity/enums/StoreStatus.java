package kr.co.delivery.db.domains.store.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    OPEN("open"),
    CLOSED("closed"),;

    private final String status;
}
