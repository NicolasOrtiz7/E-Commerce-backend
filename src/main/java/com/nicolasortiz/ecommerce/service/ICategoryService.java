package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    List<ProductCategory> findAll();

    Optional<ProductCategory> findById(int id);

    ProductCategory save(ProductCategory category);

    ProductCategory update(int id, ProductCategory category);

    ProductCategory delete(int id);

}
