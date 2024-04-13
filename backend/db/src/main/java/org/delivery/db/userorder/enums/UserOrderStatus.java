package org.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {

    ORDER("주문 대기"),
    ACCEPT("주문 수락"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("배달완료"),
    ;

    private final String status;

}
