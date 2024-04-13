package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {

    KOREAN_FOOD("한식"),
    CHINESE_FOOD("중식"),
    JAPANESE_FOOD("일식"),
    WESTERN_FOOD("양식"),
    CHICKEN("치킨"),
    PIZZA("피자"),
    HAMBURGER("햄버거"),
    FLOUR_BASED_FOOD("분식"),
    COFFEE("커피"),
    ;

    private final String category;
}
