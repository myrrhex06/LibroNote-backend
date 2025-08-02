package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.jwt.AccessTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final AccessTokenProvider accessTokenProvider;

    /**
     * Access Token 생성 처리 메서드
     *
     * @param customUserDetails 인증된 사용자 객체
     * @return String
     */
    public String generateAccessToken(CustomUserDetails customUserDetails) {
        return accessTokenProvider.createToken(customUserDetails);
    }

    /**
     * Access Token 파싱 처리 메서드
     *
     * @param token Access Token
     * @return Claims
     */
    public Claims parseAccessToken(String token) {
        return accessTokenProvider.parseToken(token);
    }

    /**
     * Access Token 검증 처리 메서드
     *
     * @param token Access Token
     * @return boolean
     */
    public boolean validateAccessToken(String token) {
        return accessTokenProvider.validateToken(token);
    }

    /**
     * Claims에 들어있는 권한 정보 추출 처리 메서드
     *
     * @param claims Access Token 파싱 결과
     * @return List<SimpleGrantedAuthority>
     */
    public List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String authorities = claims.get("authorities", String.class);

        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
