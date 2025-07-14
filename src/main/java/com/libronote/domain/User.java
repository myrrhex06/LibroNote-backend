package com.libronote.domain;

import com.libronote.common.enums.Provider;
import com.libronote.common.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long userSeq;

    private String email;

    private String password;

    private Role role;

    private String nickname;

    private Provider provider;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
