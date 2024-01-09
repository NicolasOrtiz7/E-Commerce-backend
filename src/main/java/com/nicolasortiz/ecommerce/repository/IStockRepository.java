package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStockRepository extends JpaRepository<ProductStock, Integer> {

    // Buscar stock por ProductId
    ProductStock findByProductProductId(int id);

}
