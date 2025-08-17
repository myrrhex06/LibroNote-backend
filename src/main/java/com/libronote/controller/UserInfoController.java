package com.libronote.controller;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.wrapper.ResponseWrapper;
import com.libronote.common.wrapper.ResponseWrapperUtils;
import com.libronote.controller.request.UserUpdateRequest;
import com.libronote.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users API Controller", description = "사용자 관련 API Controller")
public class UserInfoController {

    private final UserService userService;

    @Operation(summary = "사용자 목록 조회 API", description = "사용자 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> list(
            @Parameter(description = "페이지", required = true)
            @RequestParam(name = "page", required = true) Long page,
            @Parameter(description = "페이지당 보여질 데이터 개수", required = true)
            @RequestParam(name = "size", required = true) Long size,
            @Parameter(description = "닉네임", required = false)
            @RequestParam(name = "nickname", required = false) String nickname
    ){
        return ResponseWrapperUtils.success("success", userService.list(page, size, nickname));
    }

    @Operation(summary = "사용자 상제 정보 조회 API", description = "사용자 상제 정보 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    @GetMapping("/detail")
    public ResponseEntity<ResponseWrapper> detail(
            @Parameter(description = "사용자 기본키", required = true)
            @RequestParam(name = "userSeq", required = true) Long userSeq
    ){
        return ResponseWrapperUtils.success("success", userService.detail(userSeq));
    }

    @Operation(summary = "사용자 정보 수정 API", description = "사용자 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    @PatchMapping
    public ResponseEntity<ResponseWrapper> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "사용자 정보 수정 요청 객체",
                    content = @Content(schema = @Schema(implementation = UserUpdateRequest.class))
            )
            @RequestBody UserUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ){
        userService.updateUser(request, customUserDetails);
        return ResponseWrapperUtils.success("success");
    }

    @Operation(summary = "사용자 정보 삭제 API", description = "사용자 정보 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    @DeleteMapping
    public ResponseEntity<ResponseWrapper> delete(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        userService.deleteUser(customUserDetails);
        return ResponseWrapperUtils.success("success");
    }
}
