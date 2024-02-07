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
    private boolean important;
    private boolean active;

    private ProductCategory category;
    private ProductStockDto productStock;
}
