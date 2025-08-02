package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.exception.RefreshTokenNotFoundException;
import com.libronote.common.jwt.RefreshTokenProvider;
import com.libronote.domain.RefreshToken;
import com.libronote.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;
    private final RefreshTokenProvider refreshTokenProvider;

    /**
     * RefreshToken 생성 처리 메서드
     *
     * @param customUserDetails 인증된 사용자 객체
     * @return RefreshToken
     */
    public RefreshToken generateRefreshToken(CustomUserDetails customUserDetails){
        return refreshTokenProvider.createRefreshToken(customUserDetails);
    }

    /**
     * 사용자 기본키에 맞는 RefreshToken 존재 여부 확인 처리 메서드
     *
     * @param userSeq 사용자 기본키
     * @return boolean
     */
    public boolean existsRefreshToken(Long userSeq){
        return refreshTokenMapper.existsRefreshToken(userSeq);
    }

    /**
     * 토큰 값을 기준으로 RefreshToken 조회 처리 메서드
     *
     * @param tokenValue RefreshToken 값
     * @return Optional<RefreshToken>
     */
    public Optional<RefreshToken> findRefreshTokenByTokenValue(String tokenValue){
        return Optional.ofNullable(refreshTokenMapper.getRefreshToken(tokenValue));
    }

    /**
     * 사용자 기본키를 기준으로 RefreshToken 조회 처리 메서드
     *
     * @param userSeq 사용자 기본키
     * @return Optional<RefreshToken>
     */
    public Optional<RefreshToken> findRefreshTokenByUserSeq(Long userSeq){
        return Optional.of(refreshTokenMapper.getRefreshTokenByUserSeq(userSeq));
    }

    /**
     * RefreshToken 사용 여부 N 처리 메서드
     *
     * @param tokenSeq 토큰 기본키
     */
    public void unUseRefreshToken(Long tokenSeq){
        refreshTokenMapper.unUseRefreshToken(tokenSeq);
    }

    /**
     * RefreshToken 저장 처리 메서드
     *
     * @param refreshToken RefreshToken
     */
    public void saveRefreshToken(RefreshToken refreshToken){
        refreshTokenMapper.insertRefreshToken(refreshToken);
    }

    /**
     * RefreshToken 사용 여부 확인 처리 메서드
     *
     * @param refreshToken RefreshToken
     * @return boolean
     */
    public boolean checkUnUseRefreshToken(RefreshToken refreshToken){
        return refreshToken.getUseYn().equals("N");
    }

    /**
     * RefreshToken 만료 여부 검증 처리 메서드
     *
     * @param refreshToken RefreshToken
     * @return boolean
     */
    public boolean checkExpireRefreshToken(RefreshToken refreshToken){
        if(LocalDateTime.now().isAfter(refreshToken.getExpiredAt())){

            unUseRefreshToken(refreshToken.getUserSeq());

            return false;
        }

        return true;
    }

    /**
     * 사용자 기본키를 기준으로 토큰 조회 후 값이 있을 경우 사용 여부 N 처리 메서드
     *
     * @param userSeq 사용자 기본키
     */
    public void checkUserRefreshToken(Long userSeq){
        if(existsRefreshToken(userSeq)){

            RefreshToken findedRefreshToken = findRefreshTokenByUserSeq(userSeq)
                    .orElseThrow(() -> new RefreshTokenNotFoundException("해당 토큰을 찾을 수 없습니다."));

            unUseRefreshToken(findedRefreshToken.getTokenSeq());
        }
    }
}
