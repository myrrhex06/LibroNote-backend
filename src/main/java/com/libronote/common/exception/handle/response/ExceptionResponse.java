package com.libronote.common.exception.handle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    @Schema(description = "예외 메시지")
    private String message;
}
