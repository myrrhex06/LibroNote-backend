package com.libronote.common.wrapper;

import com.libronote.common.exception.handle.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseWrapper {

    @Schema(description = "상태 코드")
    private int status;

    @Schema(description = "성공 여부")
    private boolean success;

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "응답 결과")
    private Object result;

    @Schema(description = "응답 시간")
    private LocalDateTime timestamp;
}
