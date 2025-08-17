package com.libronote.service;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.enums.Provider;
import com.libronote.common.enums.Role;
import com.libronote.common.exception.*;
import com.libronote.controller.request.LoginRequest;
import com.libronote.controller.request.RefreshRequest;
import com.libronote.controller.request.RegisterRequest;
import com.libronote.controller.response.LoginResponse;
import com.libronote.controller.response.UserResponse;
import com.libronote.domain.RefreshToken;
import com.libronote.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    /**
     * 사용자 회원가입 처리 메서드
     *
     * @param request 회원가입 요청 객체
     * @return UserResponse
     */
    public UserResponse register(RegisterRequest request){
        if(userService.existsEmail(request.getEmail())){
            throw new AlreadyRegistrationException("존재하는 이메일입니다.");
        }

        if(userService.existsNickname(request.getNickname())){
            throw new AlreadyRegistrationException("존재하는 닉네임입니다.");
        }

        log.debug("회원가입 요청: {}", request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .provider(Provider.LIBRONOTE)
                .role(Role.ROLE_USER)
                .build();

        userService.saveUser(user);

        User result = userService.findUserByUserSeq(user.getUserSeq())
                .orElseThrow(() -> new RegistrationException("회원가입에 실패하였습니다."));

        return UserResponse.builder()
                .userSeq(result.getUserSeq())
                .nickname(result.getNickname())
                .email(result.getEmail())
                .provider(result.getProvider())
                .role(result.getRole())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
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

        User user = userService.findUserByEmail(customUserDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾지 못했습니다."));

        String accessToken = accessTokenService.generateAccessToken(customUserDetails);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(customUserDetails);

        refreshTokenService.checkUserRefreshToken(user.getUserSeq());

        refreshTokenService.saveRefreshToken(refreshToken);

        return LoginResponse.builder()
                .userSeq(user.getUserSeq())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getTokenValue())
                .build();
    }

    /**
     * 토큰 재발급 처리 메서드
     *
     * @param refreshRequest 토큰 재발급 요청 메서드
     * @return LoginResponse
     */
    public LoginResponse refresh(RefreshRequest refreshRequest) {
        RefreshToken originRefreshToken = refreshTokenService.findRefreshTokenByTokenValue(refreshRequest.getRefreshToken())
                .orElseThrow(() ->  new RefreshTokenNotFoundException("해당 토큰을 찾을 수 없습니다."));

        if(refreshTokenService.checkUnUseRefreshToken(originRefreshToken)){
            throw new InvalidRefreshTokenException("사용 불가능한 토큰입니다.");
        }

        if(!refreshTokenService.checkExpireRefreshToken(originRefreshToken)){
            throw new RefreshTokenExpiredException("토큰이 만료되었습니다.");
        }

        Long userSeq = originRefreshToken.getUserSeq();
        User user = userService.findUserByUserSeq(userSeq)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String accessToken = accessTokenService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userDetails);

        refreshTokenService.unUseRefreshToken(originRefreshToken.getTokenSeq());

        refreshTokenService.saveRefreshToken(refreshToken);

        return LoginResponse.builder()
                .userSeq(user.getUserSeq())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken.getTokenValue())
                .build();
    }
}
