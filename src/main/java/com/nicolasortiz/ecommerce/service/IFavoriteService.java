package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.product.ProductFavoriteDto;

import java.util.List;

public interface IFavoriteService {

    List<ProductFavoriteDto> findFavoritesByCustomerId(int id);

    void save(int productId, int customerId);

    // Elimina pasándole el id del
    void delete(int productId, int customerId);

}
