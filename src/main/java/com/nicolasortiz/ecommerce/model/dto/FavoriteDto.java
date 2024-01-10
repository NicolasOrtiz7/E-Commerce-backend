package com.nicolasortiz.ecommerce.model.dto;

import com.nicolasortiz.ecommerce.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class FavoriteDto {

    private int favoriteId;
    private Product product;

}
