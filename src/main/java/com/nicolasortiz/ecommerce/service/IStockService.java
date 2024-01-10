package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IStockService {

    Page<ProductStock> findAll(Pageable pageable);

    ProductStock findByProductId(int id);

    void save(ProductStock stock);

    void update(int productId, int quantity);

}
