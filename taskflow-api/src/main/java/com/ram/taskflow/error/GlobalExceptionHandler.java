package com.ram.taskflow.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), List.of());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiError> badRequest(BadRequestException ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), List.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<String> details = ex.getBindingResult().getFieldErrors().stream().map(this::fmt).toList();
    return build(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), details);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI(), List.of(ex.getClass().getSimpleName()));
  }

  private String fmt(FieldError e) {
    return e.getField() + ": " + (e.getDefaultMessage() == null ? "invalid" : e.getDefaultMessage());
  }

  private ResponseEntity<ApiError> build(HttpStatus status, String message, String path, List<String> details) {
    ApiError body = new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path, details);
    return ResponseEntity.status(status).body(body);
  }
}
