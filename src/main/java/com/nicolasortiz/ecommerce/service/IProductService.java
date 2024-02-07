package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.dto.product.ProductDto;
import com.nicolasortiz.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService {

    Page<ProductDto> findAll(Pageable pageable);

    Page<ProductDto> findAllActives(Pageable pageable);

    ProductDto findById(int id);

    Page<ProductDto> findByCategoryName(Pageable pageable, String name);

    Page<ProductDto> findByNameContaining(Pageable pageable, String keyword);

    List<ProductDto> findByImportant();

    void save(Product product);

    void update(int id, Product product);

    void disableOrDeleteById(int id);

}
