package com.pgvpt.exception;

import com.pgvpt.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.OffsetDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError error = new ApiError();
        error.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException ex) {
        ApiError error = new ApiError();
        error.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRuleException(BusinessRuleException ex) {
        ApiError error = new ApiError();
        error.setCode(String.valueOf(HttpStatus.CONFLICT.value()));
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableRequest(HttpMessageNotReadableException ex) {
        ApiError error = new ApiError();
        error.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        error.setMessage("Requête JSON invalide : " + ex.getMostSpecificCause().getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex) {
        ex.printStackTrace(); // ADDED FOR DEBUGGING
        ApiError error = new ApiError();
        error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        error.setMessage("Une erreur interne s'est produite: " + ex.getMessage() + (ex.getCause() != null ? " | Cause: " + ex.getCause().getMessage() : ""));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
