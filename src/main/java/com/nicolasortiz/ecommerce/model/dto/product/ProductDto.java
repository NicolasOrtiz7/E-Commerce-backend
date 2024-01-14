package com.nicolasortiz.ecommerce.model.dto.product;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDto {

    private int productId;
    private String name;
    private String description;
    private String image;
    private Long price;
    private Integer important;

    private ProductCategory category;
    private StockDto productStock;
}