package com.nicolasortiz.ecommerce.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private String message;
    private Object response;

}
