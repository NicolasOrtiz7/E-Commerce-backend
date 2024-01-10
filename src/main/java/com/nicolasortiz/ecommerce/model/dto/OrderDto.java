package com.nicolasortiz.ecommerce.model.dto;

import com.nicolasortiz.ecommerce.model.entity.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderDto {

    private CustomerDto customer;
    private LocalDateTime datetime;
    private List<OrderItems> orderItems;

}
