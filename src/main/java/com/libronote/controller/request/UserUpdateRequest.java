package com.libronote.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserUpdateRequest {

    @Schema(description = "사용자 기본키", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userSeq;

    @Schema(description = "이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;

    @Schema(description = "닉네임", example = "test", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String nickname;
}
