package com.mesago.msauth.api.exception;

import com.mesago.msauth.api.controller.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException; // <-- ¡CORRECCIÓN IMPORTANTE!
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 401 - Error de autenticación
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.warn("Fallo de autenticación: {}", ex.getMessage());
        return buildResponse(HttpStatus.UNAUTHORIZED, "No autorizado: " + ex.getMessage());
    }

    /**
     * 403 - Acceso denegado
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("Acceso denegado a '{}': {}", request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anónimo", ex.getMessage());
        return buildResponse(HttpStatus.FORBIDDEN, "No tienes permisos para acceder a este recurso.");
    }

    /**
     * 409 - Recurso duplicado
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        log.warn("Conflicto de recurso: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * 400 - Error de validación (DTOs con @Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Error de validación: {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, "Error de validación: " + errors);
    }

    /**
     * 400 - JSON malformado
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        log.warn("Error de parseo JSON: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "El cuerpo de la petición tiene un formato JSON incorrecto o valores no válidos.");
    }

    /**
     * 500 - Errores no controlados
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, WebRequest request) {
        log.error("Excepción no controlada", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado. Contacte al administrador.");
    }

    // 🔹 Método auxiliar para generar respuestas uniformes
    private ResponseEntity<ApiResponse<Void>> buildResponse(HttpStatus status, String message) {
        ApiResponse<Void> response = new ApiResponse<>(false, message, null);
        return ResponseEntity.status(status).body(response);
    }
}