package com.nicolasortiz.ecommerce.model.dto.product;

import com.nicolasortiz.ecommerce.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductFavoriteDto {

    private int favoriteId;
    private Product product;

}
