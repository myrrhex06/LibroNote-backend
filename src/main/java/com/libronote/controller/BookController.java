package com.libronote.controller;

import com.libronote.common.custom.CustomUserDetails;
import com.libronote.common.wrapper.ResponseWrapper;
import com.libronote.common.wrapper.ResponseWrapperUtils;
import com.libronote.controller.request.BookInsertRequest;
import com.libronote.controller.request.BookUpdateRequest;
import com.libronote.service.BookService;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@Tag(name = "Books API Controller", description = "책 기록 관련 API Controller")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "책 기록 등록 API", description = "책 기록 등록 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "책 기록 정보를 찾지 못할 시 반환")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> insert(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                description = "책 기록 등록 요청 객체",
                content = @Content(schema = @Schema(implementation = BookInsertRequest.class))
        )
        @RequestBody BookInsertRequest request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        return ResponseWrapperUtils.success("success", bookService.insert(request, customUserDetails));
    }

    @Operation(summary = "책 기록 목록 조회 API", description = "책 기록 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    @GetMapping
    public ResponseEntity<ResponseWrapper> list(
            @Parameter(description = "책 제목", required = false)
            @RequestParam(name = "title", required = false) String title,
            @Parameter(description = "사용자 닉네임", required = false)
            @RequestParam(name = "nickname", required = false) String nickname,
            @Parameter(description = "사용자 기본키", required = false)
            @RequestParam(name = "userSeq", required = false) Long userSeq,
            @Parameter(description = "페이지 번호", required = true)
            @RequestParam(name = "page", required = true) Long page,
            @Parameter(description = "페이지당 보여질 데이터 개수", required = true)
            @RequestParam(name = "size", required = true) Long size
    ){
        return ResponseWrapperUtils.success("success", bookService.list(title, nickname, userSeq, page, size));
    }

    @Operation(summary = "책 기록 상세 조회 API", description = "책 기록 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "책 기록을 찾지 못할 시 반환")
    })
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    @GetMapping("/detail")
    public ResponseEntity<ResponseWrapper> detail(
            @Parameter(description = "책 기록 기본키", required = true)
            @RequestParam(name = "bookSeq", required = true) Long bookSeq
    ){
        return ResponseWrapperUtils.success("success", bookService.detail(bookSeq));
    }

    @Operation(summary = "책 기록 수정 API", description = "책 기록 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "다른 사용자가 작성한 기록을 수정하려할 경우 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 또는 책 기록 정보를 찾지 못했을 경우 반환"),
    })
    @PatchMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> update(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "책 기록 수정 요청 객체",
                    content = @Content(schema = @Schema(implementation = BookUpdateRequest.class))
            )
            @RequestBody BookUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        return ResponseWrapperUtils.success("success", bookService.update(request, customUserDetails));
    }

    @Operation(summary = "책 기록 삭제 API", description = "책 기록 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "성공 시 반환"),
            @ApiResponse(responseCode = "403", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "다른 사용자의 기록을 삭제하려할 경우 반환"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))
            }, description = "사용자 또는 책 기록을 찾지 못했을 경우 반환"),
    })
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @SecurityRequirement(name = "Jwt Auth")
    public ResponseEntity<ResponseWrapper> delete(
            @RequestParam(name = "bookSeq") Long bookSeq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        bookService.delete(bookSeq, customUserDetails);
        return ResponseWrapperUtils.success("success");
    }
}
