package com.ey.exception;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
   private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
   @ExceptionHandler(ApiException.class)
   public ResponseEntity<?> handleApiException(ApiException ex) {
       logger.error("Business error: {}", ex.getMessage());
       Map<String, Object> body = new HashMap<>();
       body.put("error", ex.getMessage());
       return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
       logger.error("Validation error occurred");
       List<String> errors = ex.getBindingResult()
               .getFieldErrors()
               .stream()
               .map(DefaultMessageSourceResolvable::getDefaultMessage)
               .collect(Collectors.toList());
       Map<String, Object> body = new HashMap<>();
       body.put("errors", errors);
       return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(ResponseStatusException.class)
   public ResponseEntity<?> handleStatusException(ResponseStatusException ex) {
      Map<String, Object> body = new HashMap<>();
      body.put("error", ex.getReason());
      return new ResponseEntity<>(body, ex.getStatusCode());
   }
   
   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {
       logger.error("Invalid JSON or enum value");
       Map<String, Object> body = new HashMap<>();
       body.put("error", "Invalid request body. Please check JSON format and enum values.");
       return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<?> handleDatabaseError(DataIntegrityViolationException ex) {
       logger.error("Database constraint violation");
       Map<String, Object> body = new HashMap<>();
       body.put("error", "Invalid or duplicate data. Please check your input.");
       return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handleGenericException(Exception ex) {
       logger.error("Unexpected error occurred", ex);
       Map<String, Object> body = new HashMap<>();
       body.put("error", "Something went wrong. Please try again later.");
       return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}