package kr.co.delivery.common.security.jwt;

import java.util.Map;

public interface JwtClaims {
    Map<String, ?> getClaims();
}
