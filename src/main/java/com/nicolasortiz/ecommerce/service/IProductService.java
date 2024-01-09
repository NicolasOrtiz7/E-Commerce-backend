package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IProductService {

    //buscarTodos

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(int id);

    Page<Product> findByCategoryName(Pageable pageable, String name);

    Product save(Product product);

    Product update(int id, Product product);

    Product deleteById(int id);

}
