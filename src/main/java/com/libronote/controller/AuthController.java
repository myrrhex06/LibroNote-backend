package com.libronote.controller;

import com.libronote.controller.request.LoginRequest;
import com.libronote.controller.request.RegisterRequest;
import com.libronote.controller.response.LoginResponse;
import com.libronote.controller.response.UserResponse;
import com.libronote.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API Controller", description = "Auth 관련 API Controller")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "사용자 회원가입 API", description = "사용자 회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            }, description = "회원가입 성공 시 반환")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "사용자 회원가입 요청 객체",
                required = true,
                content = @Content(schema = @Schema(implementation = RegisterRequest.class))
        )
        @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "사용자 로그인 API", description = "사용자 로그인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            }, description = "성공 시 반환")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "로그인 요청 객체",
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
            @RequestBody LoginRequest loginRequest
    ){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
