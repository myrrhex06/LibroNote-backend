package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.enums.Provider;
import com.libronote.common.enums.Role;
import com.libronote.common.jwt.AccessTokenProvider;
import com.libronote.common.jwt.RefreshTokenProvider;
import com.libronote.controller.request.LoginRequest;
import com.libronote.controller.request.RegisterRequest;
import com.libronote.controller.response.LoginResponse;
import com.libronote.controller.response.UserResponse;
import com.libronote.domain.RefreshToken;
import com.libronote.domain.User;
import com.libronote.mapper.RefreshTokenMapper;
import com.libronote.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;
    private final RefreshTokenMapper refreshTokenMapper;

    /**
     * 사용자 회원가입 처리 메서드
     *
     * @param request 회원가입 요청 객체
     * @return UserResponse
     */
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

    /**
     * 사용자 로그인 처리 메서드
     *
     * @param loginRequest 로그인 요청 객체
     * @return LoginResponse
     */
    public LoginResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        Object principal = authenticate.getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) principal;

        User user = userMapper.findByEmail(customUserDetails.getUsername());

        if(user == null){
            throw new RuntimeException("사용자를 찾지 못했습니다.");
        }

        String accessToken = accessTokenProvider.createToken(customUserDetails);
        RefreshToken refreshToken = refreshTokenProvider.createRefreshToken(customUserDetails);

        int refreshInsertResult = refreshTokenMapper.insertRefreshToken(refreshToken);

        if(refreshInsertResult != 1){
            throw new RuntimeException("토큰 생성에 문제가 발생했습니다.");
        }

        return LoginResponse.builder()
                .userSeq(user.getUserSeq())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getTokenValue())
                .build();
    }
}
