package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<ProductCategory, Integer> {
    Optional<ProductCategory> findByName(String name);
}
