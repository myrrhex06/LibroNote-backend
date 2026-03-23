package com.libronote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncodeService {

    private final PasswordEncoder passwordEncoder;

    /**
     * Bcrypt 암호화 처리
     *
     * @param password 평문 비밀번호
     * @return 암호화 비밀번호
     */
    public String encode(String password){
        return passwordEncoder.encode(password);
    }

    /**
     * 평문, 암호화된 비밀번호 일치 여부 검증
     *
     * @param password 평문 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 일치 여부
     */
    public Boolean matches(String password, String encodedPassword){
        return passwordEncoder.matches(password, encodedPassword);
    }
}
