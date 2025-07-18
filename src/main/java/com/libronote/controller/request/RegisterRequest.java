package com.libronote.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Schema(description = "이메일", example = "test@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "비밀번호", example = "test1234!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "닉네임", example = "test", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
}
