package com.musubi.teammusubi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrCode(), e.getErrStatus().value(), e.getErrmessage());
        return new ResponseEntity<>(errorResponse, e.getErrStatus());
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String errCode;
        private int errStatus;
        private String errMessage;
    }
}
