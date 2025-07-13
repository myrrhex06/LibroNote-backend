package com.libronote.domain;

import lombok.*;

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

    private Long userSeq;
}
