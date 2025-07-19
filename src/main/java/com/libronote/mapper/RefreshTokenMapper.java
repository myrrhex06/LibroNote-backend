package com.libronote.mapper;

import com.libronote.domain.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    RefreshToken getRefreshToken(String tokenValue);

    RefreshToken getRefreshTokenByUserSeq(Long userSeq);

    int insertRefreshToken(RefreshToken refreshToken);

    int unUseRefreshToken(Long tokenSeq);

    int deleteRefreshToken(String tokenValue);
}
