package kr.co.delivery.db.domains.userorder.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserOrderStatus {
    ORDER("주문"),
    ACCEPT("수락"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("완료"),
    ;

    private final String status;
}
