package com.libronote.common.exception.handle;

import com.libronote.common.exception.AlreadyRegistrationException;
import com.libronote.common.exception.PasswordNotMatchesException;
import com.libronote.common.exception.RefreshTokenInsertFailException;
import com.libronote.common.exception.RegistrationException;
import com.libronote.common.exception.handle.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 401

    @ExceptionHandler(PasswordNotMatchesException.class)
    public ResponseEntity<ExceptionResponse> handlePasswordNotMatchesException(PasswordNotMatchesException e) {
        log.error("PasswordNotMatchesException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 404

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 409

    @ExceptionHandler(AlreadyRegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyRegistrationException(AlreadyRegistrationException e) {
        log.error("AlreadyRegistrationException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 500

    @ExceptionHandler(RefreshTokenInsertFailException.class)
    public ResponseEntity<ExceptionResponse> handleRefreshFailedException(RefreshTokenInsertFailException e) {
        log.error("RefreshTokenInsertFailException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleRegistrationException(RegistrationException e) {
        log.error("RegistrationException 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e){
        log.error("알 수 없는 에러 발생 : {}", e.getMessage());

        ExceptionResponse response = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
