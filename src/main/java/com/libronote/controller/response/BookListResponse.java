package com.libronote.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListResponse {

    @Schema(description = "책 기록 기본키")
    private Long bookSeq;

    @Schema(description = "책 제목")
    private String title;

    @Schema(description = "사용자 닉네임")
    private String nickname;

    @Schema(description = "ISBN 번호")
    private String isbn;

    @Schema(description = "파일 기본키")
    private Long fileSeq;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정일시")
    private LocalDateTime modifiedAt;
}
