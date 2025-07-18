package com.libronote.service;

import com.libronote.common.enums.Provider;
import com.libronote.common.enums.Role;
import com.libronote.controller.request.RegisterRequest;
import com.libronote.controller.request.UserResponse;
import com.libronote.domain.User;
import com.libronote.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse register(RegisterRequest request){
        if(userMapper.existsEmail(request.getEmail())){
            throw new RuntimeException("존재하는 이메일입니다.");
        }

        if(userMapper.existsNickname(request.getNickname())){
            throw new RuntimeException("존재하는 닉네임입니다.");
        }

        log.debug("회원가입 요청: {}", request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.LIBRONOTE)
                .role(Role.ROLE_USER)
                .build();

        int insertElementCount = userMapper.insertUser(user);
        log.debug("User 기본키 : {}", user.getUserSeq());

        if(0 > insertElementCount){
            throw new RuntimeException("회원가입에 실패하였습니다.");
        }

        return UserResponse.builder()
                .userSeq(user.getUserSeq())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .provider(user.getProvider())
                .role(user.getRole())
                .build();
    }
}
