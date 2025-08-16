package com.libronote.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateRequest {

    @Schema(description = "책 기록 기본키", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long bookSeq;

    @Schema(description = "책 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(description = "책 줄거리", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "ISBN 번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String isbn;

    @Schema(description = "느낀점", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String feeling;

    @Schema(description = "이미지 파일 기본키", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long fileSeq;
}
