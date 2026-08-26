package com.pgvpt.exception;

import com.pgvpt.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class PatrimoineExceptionHandler {

    @ExceptionHandler(PatrimoineNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(PatrimoineNotFoundException ex, WebRequest request) {
        ApiError error = new ApiError();
        error.setTimestamp(OffsetDateTime.now());
        error.setStatus(404);
        error.setError("Not Found");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(PatrimoineValidationException.class)
    public ResponseEntity<ApiError> handleValidation(PatrimoineValidationException ex, WebRequest request) {
        ApiError error = new ApiError();
        error.setTimestamp(OffsetDateTime.now());
        error.setStatus(400);
        error.setError("Bad Request");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(PatrimoineBusinessException.class)
    public ResponseEntity<ApiError> handleBusinessRule(PatrimoineBusinessException ex, WebRequest request) {
        ApiError error = new ApiError();
        error.setTimestamp(OffsetDateTime.now());
        error.setStatus(422);
        error.setError("Unprocessable Entity");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(422).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, WebRequest request) {
        ApiError error = new ApiError();
        error.setTimestamp(OffsetDateTime.now());
        error.setStatus(500);
        error.setError("Internal Server Error");
        error.setMessage(ex.getMessage());
        error.setPath(request.getDescription(false).replace("uri=", ""));
        return ResponseEntity.status(500).body(error);
    }
}
