package com.nicolasortiz.ecommerce.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {

    private String message;
    private LocalDateTime datetime;
    private String uri;
}
