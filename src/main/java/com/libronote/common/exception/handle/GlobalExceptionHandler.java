package com.libronote.common.exception.handle;

import com.libronote.common.exception.*;
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

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileNameException.class)
    public ResponseEntity<ResponseWrapper> handleInvalidFileNameException(InvalidFileNameException e) {
        log.error("InvalidFileNameException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidExtensionException.class)
    public ResponseEntity<ResponseWrapper> handleInvalidExtensionException(InvalidExtensionException e) {
        log.error("InvalidExtensionException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyRegistrationException.class)
    public ResponseEntity<ResponseWrapper> handleAlreadyRegistrationException(AlreadyRegistrationException e) {
        log.error("AlreadyRegistrationException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ResponseWrapper> handleInvalidRefreshToken(InvalidRefreshTokenException e) {
        log.error("InvalidRefreshTokenException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ResponseWrapper> handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        log.error("RefreshTokenExpiredException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 404

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        log.error("RefreshTokenNotFoundException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResponseWrapper> handleFileNotFoundException(FileNotFoundException e) {
        log.error("FileNotFoundException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 500

    @ExceptionHandler(RefreshTokenInsertFailException.class)
    public ResponseEntity<ResponseWrapper> handleRefreshFailedException(RefreshTokenInsertFailException e) {
        log.error("RefreshTokenInsertFailException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileDownloadFailException.class)
    public ResponseEntity<ResponseWrapper> handleInvalidFileException(FileDownloadFailException e) {
        log.error("InvalidFileException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RefreshTokenUpdateFailException.class)
    public ResponseEntity<ResponseWrapper> handleRefreshFailedException(RefreshTokenUpdateFailException e) {
        log.error("RefreshTokenUpdateFailException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ResponseWrapper> handleRegistrationException(RegistrationException e) {
        log.error("RegistrationException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileUploadFailException.class)
    public ResponseEntity<ResponseWrapper> handleFileUploadFailException(FileUploadFailException e) {
        log.error("FileUploadFailException 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper> handleException(Exception e){
        log.error("알 수 없는 에러 발생 : {}", e.getMessage());

        return ResponseWrapperUtils.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
