package com.nicolasortiz.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoStockException extends RuntimeException{
    public NoStockException(String message) {
        super(message);
    }
}
