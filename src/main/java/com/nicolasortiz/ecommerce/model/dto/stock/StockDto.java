package com.nicolasortiz.ecommerce.model.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {

    private int stockId;
    private int quantity;
    private StockProductDto product;

}
