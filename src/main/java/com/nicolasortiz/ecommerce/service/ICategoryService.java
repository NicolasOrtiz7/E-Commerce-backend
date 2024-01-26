package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;

import java.util.List;

public interface ICategoryService {

    List<ProductCategory> findAll();

    ProductCategory findById(int id);

    void save(ProductCategory category);

    void update(int id, ProductCategory category);

    void delete(int id);

}
