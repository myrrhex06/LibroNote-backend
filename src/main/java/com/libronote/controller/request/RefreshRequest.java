package com.libronote.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {

    @Schema(description = "Refresh 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
    private String refreshToken;
}
