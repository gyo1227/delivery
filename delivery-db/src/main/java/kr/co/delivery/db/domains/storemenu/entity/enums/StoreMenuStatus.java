package kr.co.delivery.db.domains.storemenu.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreMenuStatus {

    IN_STOCK("재고있음"),
    OUT_OF_STOCK("품절")
    ;

    private final String status;
}
