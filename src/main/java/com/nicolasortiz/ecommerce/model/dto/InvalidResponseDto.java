package com.nicolasortiz.ecommerce.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InvalidResponseDto{

    private String message;
    private LocalDateTime datetime;
    private String uri;
    private Object response;
}
