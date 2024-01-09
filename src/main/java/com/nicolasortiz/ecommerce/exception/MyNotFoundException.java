package com.nicolasortiz.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException{
    public MyNotFoundException(String message) {
        super(message);
    }
}
