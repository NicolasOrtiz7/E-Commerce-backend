package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByCategoryNameAndActiveIsTrue(Pageable pageable, String name);

    List<Product> findByImportantAndActiveIsTrue(boolean isImportant);

    Page<Product> findByNameContainingAndActiveIsTrue(Pageable pageable, String keyword);

    Page<Product> findAllByActiveIsTrue(Pageable pageable);

}
