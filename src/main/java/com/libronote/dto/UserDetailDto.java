package com.libronote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {

    private Long userSeq;

    private String email;

    private String nickname;

    private Long totalBooks;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
