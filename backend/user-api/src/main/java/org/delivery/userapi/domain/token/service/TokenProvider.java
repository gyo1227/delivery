package org.delivery.userapi.domain.token.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.error.TokenErrorCode;
import org.delivery.common.exception.ApiException;
import org.delivery.db.user.enums.UserRole;
import org.delivery.userapi.domain.token.model.TokenDto;
import org.delivery.userapi.domain.user.model.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
public class TokenProvider {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    public TokenDto issueAccessToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);

        Date now = new Date();
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var token = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    public TokenDto issueRefreshToken(Map<String, Object> data) {
        var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);

        Date now = new Date();
        var expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault())
                        .toInstant());

        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var token = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    public Map<String, Object> validationToken(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        try{
            var result = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(result.getBody());
        }catch (Exception e){
            if(e instanceof SignatureException){
                // 토큰이 유효하지 않을때
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            }
            else if(e instanceof ExpiredJwtException){
                //  만료된 토큰
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
            else{
                // 그외 에러
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();

        UserDetails principal = UserSession.builder()
                .id(Long.parseLong(claims.get("userId").toString()))
                .role(UserRole.valueOf(claims.get("userRole").toString()))
                .build();

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

}
