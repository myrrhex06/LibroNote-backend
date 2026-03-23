package com.libronote.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse<T> {

    @Schema(description = "결과")
    private T content;

    @Schema(description = "전체 요소 수")
    private int totalElements;

    @Schema(description = "전체 페이지 수")
    private int totalPages;

    @Schema(description = "현재 페이지")
    private int page;

    @Schema(description = "현재 사이즈")
    private int size;
}
