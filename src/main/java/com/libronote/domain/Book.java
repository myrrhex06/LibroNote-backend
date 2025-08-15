package com.libronote.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long bookSeq;

    private String title;

    private String content;

    private String isbn;

    private String imgUrl;

    private String feeling;

    private Long userSeq;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
