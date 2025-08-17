package com.libronote.common.jwt;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.domain.RefreshToken;
import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private final UserMapper userMapper;

    @Value("${jwt.refresh.expiredms}")
    private long refreshExpiredMs;

    /**
     * RefreshToken 생성 메서드
     *
     * @param userDetails 인증된 사용자 객체
     * @return RefreshToken
     */
    public RefreshToken createRefreshToken(CustomUserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = userMapper.findByEmail(email);

        Date now = new Date();
        Date expired = new Date(now.getTime() + refreshExpiredMs);

        RefreshToken refresh = RefreshToken.builder()
                .tokenValue(UUID.randomUUID().toString())
                .useYn("Y")
                .userSeq(user.getUserSeq())
                .issuedAt(now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .expiredAt(expired.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();

        return refresh;
    }
}
