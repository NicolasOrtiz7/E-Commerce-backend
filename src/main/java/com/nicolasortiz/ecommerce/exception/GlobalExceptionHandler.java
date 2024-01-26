package com.nicolasortiz.ecommerce.exception;

import com.nicolasortiz.ecommerce.model.dto.ErrorResponseDto;
import com.nicolasortiz.ecommerce.model.dto.InvalidResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(InvalidResponseDto.builder()
                        .message("ha ocurrido un error")
                        .datetime(LocalDateTime.now())
                        .response(errors)
                        .build());
    }

    @ExceptionHandler(MyNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(
            HttpServletRequest request,
            MyNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder()
                        .message(ex.getMessage())
                        .datetime(LocalDateTime.now())
                        .uri(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(MyExistingObjectException.class)
    public ResponseEntity<ErrorResponseDto> existingObjectException(
            HttpServletRequest request,
            MyExistingObjectException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .message(ex.getMessage())
                        .datetime(LocalDateTime.now())
                        .uri(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(NoStockException.class)
    public ResponseEntity<ErrorResponseDto> noStockException(HttpServletRequest request,
                                                             NoStockException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .message(ex.getMessage())
                        .datetime(LocalDateTime.now())
                        .uri(request.getRequestURI())
                        .build());
    }

}
