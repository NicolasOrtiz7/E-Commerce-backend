package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findById(int id);

    Page<ProductDto> findByCategoryName(Pageable pageable, String name);

    void save(Product product);

    void update(int id, Product product);

    void deleteById(int id);

}
