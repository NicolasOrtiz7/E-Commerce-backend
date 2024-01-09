package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByName(String name);
}
