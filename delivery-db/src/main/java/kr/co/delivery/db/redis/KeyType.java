package kr.co.delivery.db.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeyType {

    USER("user");

    private final String prefix;
}
