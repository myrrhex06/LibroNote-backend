package com.libronote.common.jwt;

import com.libronote.common.custom.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AccessTokenProvider {

    private final Key SECRET_KEY;
    private final long ACCESS_EXPIRED;
    public AccessTokenProvider(
            @Value("${jwt.secret.key}") String secretKey,
            @Value("${jwt.access.expiredms}") long accessExpire
    ){
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.ACCESS_EXPIRED = accessExpire;
    }

    /**
     * ACCESS TOKEN 생성 메서드
     *
     * @param customUserDetails 인증된 사용자 객체
     * @return String
     */
    public String createToken(CustomUserDetails customUserDetails){
        log.debug("JWT 토큰 생성 메서드 호출");

        String authorities = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRED))
                .compact();
    }

    /**
     * ACCESS TOKEN 파싱 처리 메서드
     *
     * @param token ACCESS TOKEN
     * @return Claims
     */
    public Claims parseToken(String token){
        log.debug("JWT 토큰 파싱 메서드 호출");

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Access Token 유효성 검증 메서드
     *
     * @param token Access Token
     * @return boolean
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);

            log.debug("JWT 토큰 Validation 성공");

            return true;
        }catch(ExpiredJwtException e){
            log.debug("토큰이 만료되었습니다. : {}", e.getMessage());
            return false;
        }catch(UnsupportedJwtException e){
            log.debug("잘못된 형식의 토큰입니다. : {}", e.getMessage());
            return false;
        }catch(MalformedJwtException e){
            log.debug("잘못된 포맷을 가진 토큰입니다. : {}", e.getMessage());
            return false;
        }catch(SignatureException e){
            log.debug("서명이 유효하지 않습니다. : {}", e.getMessage());
            return false;
        }catch(IllegalArgumentException e){
            log.debug("잘못된 값입니다. : {}", e.getMessage());
            return false;
        }catch(Exception e){
            log.debug("알 수 없는 예외가 발생했습니다. : {}", e.getMessage());
            return false;
        }
    }
}
