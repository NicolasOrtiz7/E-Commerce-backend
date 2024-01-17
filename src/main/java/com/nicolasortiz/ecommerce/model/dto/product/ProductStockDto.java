package com.nicolasortiz.ecommerce.model.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductStockDto {

    private int stockId;
    private int quantity;
}
