package com.nicolasortiz.ecommerce.model.dto.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class OrderCategoryRes {

    private int categoryId;
    private String name;

}
