package com.nicolasortiz.ecommerce.model.dto.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class StockProductDto {

    private int productId;

    private String name;
    private String image;

    private Long price;
    private boolean important;
}
