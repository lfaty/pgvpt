package com.pgvpt.exception;

import com.pgvpt.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError error = createBaseApiError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(InvalidRequestException ex, HttpServletRequest request) {
        ApiError error = createBaseApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableRequest(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String detailMessage = ex.getMostSpecificCause().getMessage();
        ApiError error = createBaseApiError(HttpStatus.BAD_REQUEST, "Requête JSON invalide : " + detailMessage, request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, HttpServletRequest request) {
        // Journalisation de la stacktrace côté serveur pour le débogage (Remplace ex.printStackTrace())
        log.error("Erreur interne non gérée sur l'URI : {}", request.getRequestURI(), ex);

        ApiError error = createBaseApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur interne imprévue s'est produite sur le serveur.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessRuleException(BusinessException ex, HttpServletRequest request) {
        // Mapping sémantique vers un statut 409 Conflict pour les violations de règles métier
        ApiError error = createBaseApiError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    private ApiError createBaseApiError(HttpStatus status, String message, String path) {
        ApiError error = new ApiError();
        error.setTimestamp(OffsetDateTime.now());
        error.setStatus(status.value());
        error.setCode(String.valueOf(status.value()));
        error.setMessage(message);
        error.setPath(path);
        return error;
    }

}

