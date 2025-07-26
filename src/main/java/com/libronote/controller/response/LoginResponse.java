package com.libronote.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @Schema(description = "사용자 기본키")
    private Long userSeq;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "access 토큰")
    private String accessToken;

    @Schema(description = "refresh 토큰")
    private String refreshToken;
}
