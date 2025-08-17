package com.libronote.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListDto {

    private Long bookSeq;

    private String title;

    private String isbn;

    private String nickname;

    private Long fileSeq;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
