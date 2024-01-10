package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.FavoriteDto;

import java.util.List;

public interface IFavoriteService {

    List<FavoriteDto> findFavoritesByCustomerId(int id);

    void save(int productId, int customerId);

    // Elimina pasandole el id del
    void delete(int productId, int customerId);

}
