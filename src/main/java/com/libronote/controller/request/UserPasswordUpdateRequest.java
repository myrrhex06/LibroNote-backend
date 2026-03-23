package com.libronote.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateRequest {

    @Schema(description = "기존 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @Schema(description = "새 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
