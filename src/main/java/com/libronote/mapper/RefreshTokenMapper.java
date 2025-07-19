package com.libronote.mapper;

import com.libronote.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    /**
     * RefreshToken 조회 메서드
     *
     * @param tokenValue RefreshToken 값
     * @return RefreshToken
     */
    RefreshToken getRefreshToken(String tokenValue);

    /**
     * 사용자 기본키를 통한 RefreshToken 조회 메서드
     *
     * @param userSeq 사용자 기본키
     * @return RefreshToken
     */
    RefreshToken getRefreshTokenByUserSeq(Long userSeq);

    /**
     * RefreshToken 저장 메서드
     *
     * @param refreshToken RefreshToken 객체
     * @return int
     */
    int insertRefreshToken(RefreshToken refreshToken);

    /**
     * RefreshToken 사용 안함 처리 메서드
     *
     * @param tokenSeq RefreshToken 기본키
     * @return int
     */
    int unUseRefreshToken(Long tokenSeq);

    /**
     * RefreshToken 제거 메서드
     *
     * @param tokenValue RefreshToken 값
     * @return int
     */
    int deleteRefreshToken(String tokenValue);
}
