package com.libronote.controller.response;

import com.libronote.common.enums.Provider;
import com.libronote.common.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    @Schema(description = "사용자 기본키", example = "1")
    private Long userSeq;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "권한")
    private Role role;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "인증 제공자")
    private Provider provider;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private LocalDateTime modifiedAt;
}
