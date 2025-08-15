package com.libronote.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponse {

    @Schema(description = "파일 기본키")
    private Long fileSeq;

    @Schema(description = "사용자 기본키")
    private Long userSeq;

    @Schema(description = "이미지 저장 URL")
    private String imageUrl;

    @Schema(description = "파일명")
    private String fileName;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private LocalDateTime modifiedAt;
}
