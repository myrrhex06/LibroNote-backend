package com.libronote.controller;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.wrapper.ResponseWrapper;
import com.libronote.common.wrapper.ResponseWrapperUtils;
import com.libronote.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "Image File API Controller", description = "이미지 파일 관련 API")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "책 표지 이미지 업로드 API", description = "책 표지 이미지 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "500", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "파일 업로드 실패 시 발생")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> upload(
            @Parameter(description = "업로드 파일", required = true)
            @RequestParam(name = "file", required = true) MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        return ResponseWrapperUtils.success("success",  fileService.upload(file, customUserDetails));
    }
}
