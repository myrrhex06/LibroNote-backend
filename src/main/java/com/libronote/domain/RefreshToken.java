package com.libronote.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    private Long tokenSeq;

    private String tokenValue;

    private String useYn;

    private Long userSeq;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;
}
