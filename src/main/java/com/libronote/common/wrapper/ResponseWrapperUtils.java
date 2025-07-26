package com.libronote.common.wrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResponseWrapperUtils {

    public static ResponseEntity<ResponseWrapper> success(String message, Object result){

        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(true)
                .message(message)
                .result(result)
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(responseWrapper);
    }

    public static ResponseEntity<ResponseWrapper> success(String message){
        return success(message, null);
    }

    public static ResponseEntity<ResponseWrapper> fail(HttpStatus status, List<Object> errorMessages){
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .success(false)
                .status(status.value())
                .errorMessage(errorMessages)
                .message("fail")
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(responseWrapper, status);
    }

    public static ResponseEntity<ResponseWrapper> fail(HttpStatus status, Object exceptionResponse){

        List<Object> errorMessage = new ArrayList<>();
        errorMessage.add(exceptionResponse);

        return fail(status, errorMessage);
    }
}
