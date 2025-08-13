package com.libronote.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {

    private Long fileSeq;

    private Long userSeq;

    private String imageUrl;

    private String fileName;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
