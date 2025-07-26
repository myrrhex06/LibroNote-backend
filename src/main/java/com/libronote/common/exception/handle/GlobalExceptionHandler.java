package com.libronote.common.exception.handle;

import com.libronote.common.exception.AlreadyRegistrationException;
import com.libronote.common.exception.PasswordNotMatchesException;
import com.libronote.common.exception.RefreshTokenInsertFailException;
import com.libronote.common.exception.RegistrationException;
import com.libronote.common.exception.handle.response.ExceptionResponse;
import com.libronote.common.wrapper.ResponseWrapper;
import com.libronote.common.wrapper.ResponseWrapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 400

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ResponseWrapper> handlePasswordNotMatchesException(PasswordNotMatchesException e) {
        log.error("PasswordNotMatchesException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.BAD_REQUEST, response);
    }

    @ExceptionHandler(AlreadyRegistrationException.class)
    public ResponseEntity<ResponseWrapper> handleAlreadyRegistrationException(AlreadyRegistrationException e) {
        log.error("AlreadyRegistrationException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.BAD_REQUEST, response);
    }

    // 404

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.NOT_FOUND, response);
    }

    // 500

    @ExceptionHandler(RefreshTokenInsertFailException.class)
    public ResponseEntity<ResponseWrapper> handleRefreshFailedException(RefreshTokenInsertFailException e) {
        log.error("RefreshTokenInsertFailException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.INTERNAL_SERVER_ERROR, response);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ResponseWrapper> handleRegistrationException(RegistrationException e) {
        log.error("RegistrationException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.INTERNAL_SERVER_ERROR, response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleException(Exception e){
        log.error("알 수 없는 에러 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .build();

        return ResponseWrapperUtils.fail(HttpStatus.INTERNAL_SERVER_ERROR, response);
    }
}
