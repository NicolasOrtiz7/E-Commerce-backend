package com.nicolasortiz.ecommerce.model.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderProductRes {

    private int productId;
    private String name;
    private Long price;
    private OrderCategoryRes category;
}
