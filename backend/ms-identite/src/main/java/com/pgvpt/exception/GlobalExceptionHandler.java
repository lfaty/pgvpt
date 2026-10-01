package com.pgvpt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException exception) {

        ApiError error = new ApiError(LocalDateTime.now(), 404, "NOT_FOUND", exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException exception) {

        ApiError error = new ApiError(LocalDateTime.now(), 400, "BAD_REQUEST", exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException exception) {

        ApiError error = new ApiError(LocalDateTime.now(), 409, "BUSINESS_ERROR", exception.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    public record ApiError(LocalDateTime timestamp, int status, String code, String message) {
    }
}
