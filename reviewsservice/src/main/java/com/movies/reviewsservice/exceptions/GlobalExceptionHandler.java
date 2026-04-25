package com.movies.reviewsservice.exceptions;


import feign.FeignException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //it listens if from any part of the code below exception class is thrown
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleAlreadyExists(EntityExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotExists(EntityNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex){
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleFeignNotFound(FeignException.NotFound ex,
                                                                   HttpServletRequest request) {
    //    log.error("Movie not found at [{}]: {}", request.getRequestURI(), ex.getMessage());

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Cannot add review: movie does not exist");
        error.put("path", request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<Map<String, Object>> handleFeignForbidden(FeignException.Forbidden ex,
                                                                    HttpServletRequest request) {
     //   log.error("Forbidden access at [{}]: {}", request.getRequestURI(), ex.getMessage());

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.FORBIDDEN.value());
        error.put("error", "You are not authorized to access this movie");
        error.put("path", request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
