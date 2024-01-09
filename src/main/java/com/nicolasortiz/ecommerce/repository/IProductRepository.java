package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
}
