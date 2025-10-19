package com.mesago.msauth.api.exception;

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
     * Error 401: Fallo de autenticación.
     * Se activa cuando el login falla (contraseña incorrecta, usuario no existe, cuenta deshabilitada/bloqueada).
     */
    @ExceptionHandler(value = { AuthenticationException.class })
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.warn("Fallo de autenticación: {}", ex.getMessage());
        Map<String, Object> body = createErrorBody(HttpStatus.UNAUTHORIZED, "No Autorizado", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Error 403: Acceso denegado (Prohibido).
     * Se activa cuando un usuario autenticado intenta acceder a un recurso para el cual no tiene el rol necesario.
     */
    @ExceptionHandler(value = { AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("Acceso denegado para el usuario '{}': {}", request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anónimo", ex.getMessage());
        Map<String, Object> body = createErrorBody(HttpStatus.FORBIDDEN, "Prohibido", "No tienes los permisos necesarios para acceder a este recurso.", request);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    /**
     * Error 409: Conflicto de recursos.
     * Se activa con nuestra excepción personalizada cuando se intenta crear un recurso que ya existe (ej. username/email duplicado).
     */
    @ExceptionHandler(value = { DuplicateResourceException.class })
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        log.warn("Conflicto de recurso: {}", ex.getMessage());
        Map<String, Object> body = createErrorBody(HttpStatus.CONFLICT, "Conflicto", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Error 400: Petición incorrecta por validación de DTOs.
     * Se activa cuando los datos de un @RequestBody anotado con @Valid no cumplen las reglas (@NotBlank, @Email, etc.).
     */
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Error de validación: {}", errors);
        Map<String, Object> body = createErrorBody(HttpStatus.BAD_REQUEST, "Petición Inválida", "Error de validación: " + errors, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error 400: Petición incorrecta por JSON malformado.
     * Se activa cuando el JSON de la petición no se puede convertir a un objeto Java (ej. rol "GERENTA").
     */
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleJsonParseException(HttpMessageNotReadableException ex, WebRequest request) {
        log.warn("Error de parseo de JSON: {}", ex.getMessage());
        Map<String, Object> body = createErrorBody(HttpStatus.BAD_REQUEST, "Petición Inválida", "El cuerpo de la petición tiene un formato JSON incorrecto o valores no válidos.", request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error 500: Error interno del servidor (último recurso).
     * Atrapa cualquier otra excepción no controlada para evitar exponer detalles sensibles.
     */
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        log.error("¡Ocurrió una excepción no controlada!", ex);
        Map<String, Object> body = createErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno del Servidor", "Ocurrió un error inesperado. Contacte al administrador.", request);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createErrorBody(HttpStatus status, String error, String message, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", request.getDescription(false).substring(4)); // Quita "uri="
        return body;
    }
}