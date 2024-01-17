package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyExistingObjectException;
import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.product.ProductFavoriteDto;
import com.nicolasortiz.ecommerce.model.entity.CustomerFavorites;
import com.nicolasortiz.ecommerce.model.mapper.FavoriteMapper;
import com.nicolasortiz.ecommerce.repository.IFavoriteRepository;
import com.nicolasortiz.ecommerce.service.IFavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements IFavoriteService {

    private final IFavoriteRepository favoriteRepository;

    @Override
    public List<ProductFavoriteDto> findFavoritesByCustomerId(int id) {
        return favoriteRepository.findByCustomerCustomerId(id).stream()
                .map(FavoriteMapper.INSTANCE::toDto).toList();
    }

    @Transactional
    @Override
    // todo: buscar si existe el usuario y el producto, sino lanza error 500
    public void save(int productId, int customerId) {
        // Busca si el producto ya está agregado a la lista de favoritos
        Optional<CustomerFavorites> productFound = favoriteRepository
                .findExistingFavorite(productId, customerId);

        if (productFound.isPresent()){
            throw new MyExistingObjectException("Ya lo agregaste a favoritos");
        }

        // Guarda el producto en la lista de favoritos
        favoriteRepository.addFavorite(productId, customerId);
    }

    @Transactional
    @Override
    public void delete(int productId, int customerId) {
        CustomerFavorites productFound = favoriteRepository
                .findExistingFavorite(productId, customerId)
                .orElseThrow(()-> new MyNotFoundException("No tienes ese producto en favoritos"));

        favoriteRepository.deleteFavorite(productId, customerId);
    }
}

